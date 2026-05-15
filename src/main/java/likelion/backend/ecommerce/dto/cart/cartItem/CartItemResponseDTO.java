package likelion.backend.ecommerce.dto.cart.cartItem;

import likelion.backend.ecommerce.entity.cart.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemResponseDTO {
    private Long cartItemId;
    private Long productId;
    private Integer curProductPrice;
    private Integer count;
    private Integer totalPrice;

    public CartItemResponseDTO(CartItem item){
        this.cartItemId = item.getCartItemId();
        this.productId = item.getProductId();
        this.curProductPrice = item.getCurProductPrice();
        this.count = item.getCount();
        this.totalPrice = item.getTotalProductPrice();
    }
}
