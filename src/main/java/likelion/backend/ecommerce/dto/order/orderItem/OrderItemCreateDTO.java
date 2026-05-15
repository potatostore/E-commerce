package likelion.backend.ecommerce.dto.order.orderItem;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemCreateDTO {
    private Long productId;

    @Min(value = 0)
    private Integer curProductPrice;

    @Min(value = 0)
    private Integer count;
}
