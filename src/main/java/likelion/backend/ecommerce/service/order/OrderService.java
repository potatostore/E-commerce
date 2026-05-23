package likelion.backend.ecommerce.service.order;

import jakarta.transaction.Transactional;
import likelion.backend.ecommerce.dto.order.OrderCreateDTO;
import likelion.backend.ecommerce.dto.order.OrderResponseDTO;
import likelion.backend.ecommerce.entity.cart.Cart;
import likelion.backend.ecommerce.entity.order.Order;
import likelion.backend.ecommerce.entity.payment.Payment;
import likelion.backend.ecommerce.entity.product.Product;
import likelion.backend.ecommerce.global.client.PaymentClient;
import likelion.backend.ecommerce.global.exception.Errorcode;
import likelion.backend.ecommerce.global.exception.NotFoundException;
import likelion.backend.ecommerce.global.exception.OutOfStockException;
import likelion.backend.ecommerce.repository.Payment.PaymentRepository;
import likelion.backend.ecommerce.repository.cart.CartRepository;
import likelion.backend.ecommerce.repository.order.OrderRepository;
import likelion.backend.ecommerce.repository.product.ProductRepository;
import likelion.backend.ecommerce.status.payment.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentClient paymentClient;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponseDTO createOrder(Long userId, OrderCreateDTO orderCreateDTO){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(Errorcode.CART_EMPTY);
        }

        Cart cart = cartRepository.findByUserId(userId);

        Order order = Order.builder().userId(userId).cart(cart).build();

        Order savedOrder = orderRepository.save(order);

        List<Product> updatedProducts = cart.getCartItemList().stream()
                .map(cartItem -> {
                    Product product = productRepository.findById(cartItem.getProductId())
                            .orElseThrow(() -> new NotFoundException(
                                    Errorcode.PRODUCT_NOT_FOUND
                            ));

                    if (product.getQuantity() < cartItem.checkQuantity()) {
                        throw new OutOfStockException(
                                Errorcode.PRODUCT_OUT_OF_STOCK
                        );
                    }

                    product.updateProductQuantity(-cartItem.getQuantity());
                    product.updateProductStatus();

                    return product;
                })
                .toList();

        Payment mockPayment = Payment.builder()
                .orderId(savedOrder.getOrderId())
                .userId(userId)
                .payAmount(savedOrder.getTotalOrderPrice())
                .paymentMethod(PaymentMethod.CARD) // 기본값으로 카드 세팅
                .build();

        boolean isSuccess = paymentClient.verifyPaymentWithPG(
                orderCreateDTO.getPaymentToken(),
                order.getTotalOrderPrice()
        );

        if(!isSuccess){
            throw new RuntimeException("결제가 정상적으로 처리되지 않았습니다.");
        }

        paymentRepository.save(mockPayment);

        cart.getCartItemList().clear();
        cart.updateTotalCartPrice();

        return new OrderResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> findAllOrders(){
        List<Order> orderList = orderRepository.findAll();

        if(orderList == null){
            throw new RuntimeException("주문정보를 조회할 수 없습니다.");
        }

        return orderList.stream().map(item -> new OrderResponseDTO(item)).toList();
    }

    public OrderResponseDTO findOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(Errorcode.ORDER_NOT_FOUND));

        return new OrderResponseDTO(order);
    }

    public OrderResponseDTO deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(Errorcode.ORDER_NOT_FOUND));

        orderRepository.delete(order);

        return new OrderResponseDTO(order);
    }
}
