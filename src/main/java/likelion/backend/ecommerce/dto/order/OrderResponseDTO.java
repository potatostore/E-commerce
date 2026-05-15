package likelion.backend.ecommerce.dto.order;

import likelion.backend.ecommerce.dto.order.orderItem.OrderItemResponseDTO;
import likelion.backend.ecommerce.entity.order.Order;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderResponseDTO {
    private Long userId;
    private List<OrderItemResponseDTO> orderItemList;
    private Integer totalCartPrice;

    public OrderResponseDTO(Order order){
        this.userId = order.getUserId();
        this.orderItemList = order.getOrderItemList().stream()
                .map(OrderItemResponseDTO::new).collect(Collectors.toList());
        this.totalCartPrice = orderItemList.stream().mapToInt(OrderItemResponseDTO::getTotalPrice).sum();
    }
}
