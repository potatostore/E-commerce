package likelion.backend.ecommerce.service.order;

import jakarta.transaction.Transactional;
import likelion.backend.ecommerce.dto.order.OrderCreateDTO;
import likelion.backend.ecommerce.dto.order.OrderResponseDTO;
import likelion.backend.ecommerce.entity.cart.Cart;
import likelion.backend.ecommerce.entity.order.Order;
import likelion.backend.ecommerce.entity.payment.Payment;
import likelion.backend.ecommerce.global.client.PaymentClient;
import likelion.backend.ecommerce.global.exception.NotFoundException;
import likelion.backend.ecommerce.repository.Payment.PaymentRepository;
import likelion.backend.ecommerce.repository.cart.CartRepository;
import likelion.backend.ecommerce.repository.order.OrderRepository;
import likelion.backend.ecommerce.status.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentClient paymentClient;

    @Transactional
    public OrderResponseDTO createOrder(Long userId, OrderCreateDTO orderCreateDTO){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + " : 회원의 장바구니에 아무 상품이 담겨있지 않습니다.");
        }

        Cart cart = cartRepository.findByUserId(userId);

        Order order = Order.builder().userId(userId).cart(cart).build();

        Order savedOrder = orderRepository.save(order);

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

    public OrderResponseDTO findOrderById(Long userId){
        if(!orderRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + " : 회원의 주문정보를 조회할 수 없습니다.");
        }

        Order order = orderRepository.findByUserId(userId);

        return new OrderResponseDTO(order);
    }

    public OrderResponseDTO deleteOrder(Long userId){
        if(!orderRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + " : 회원의 주문정보를 찾을 수 없습니다.");
        }

        Order order = orderRepository.findByUserId(userId);

        orderRepository.delete(order);

        return new OrderResponseDTO(order);
    }
}
