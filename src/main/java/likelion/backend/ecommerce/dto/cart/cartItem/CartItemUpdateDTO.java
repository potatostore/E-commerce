package likelion.backend.ecommerce.dto.cart.cartItem;

import lombok.Getter;

@Getter
public class CartItemUpdateDTO {
    private Long productId;
    private Integer curProductPrice;
    private Integer count;
}
