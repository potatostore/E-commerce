package likelion.backend.ecommerce.dto.cart.cartItem;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemCreateDTO {
    private Long productId;

    @Min(value = 0)
    private Integer curProductPrice;

    @Min(value = 0)
    private Integer quantity;
}
