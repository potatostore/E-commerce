package likelion.backend.ecommerce.dto.order.orderItem;

import likelion.backend.ecommerce.entity.order.Order;
import likelion.backend.ecommerce.entity.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long cartItemId;
    private Long productId;
    private Integer curProductPrice;
    private Integer count;
    private Integer totalPrice;

    public OrderItemResponseDTO (OrderItem item){
        this.cartItemId = item.getOrderItemId();
        this.productId = item.getProductId();
        this.curProductPrice = item.getCurProductPrice();
        this.count = item.getQuantity();
        this.totalPrice = item.getTotalProductPrice();
    }
}
