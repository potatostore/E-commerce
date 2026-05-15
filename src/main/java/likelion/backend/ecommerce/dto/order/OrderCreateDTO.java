package likelion.backend.ecommerce.dto.order;

public class OrderCreateDTO {
    private Long userId;
    private List<OrderItem> orderItemList = new ArrayList<>();
}
