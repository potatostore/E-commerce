package likelion.backend.ecommerce.dto.cart;

import likelion.backend.ecommerce.dto.cart.cartItem.CartItemResponseDTO;
import likelion.backend.ecommerce.entity.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Setter
public class CartResponseDTO {
    private Long userId;
    private List<CartItemResponseDTO> cartItemList;
    private Integer totalCartPrice;

    public CartResponseDTO(Cart cart){
        this.userId = cart.getUserId();
        this.cartItemList = cart.getCartItemList().stream()
                .map(CartItemResponseDTO::new).collect(Collectors.toList());
        this.totalCartPrice = cart.getTotalCartPrice();
    }
}
