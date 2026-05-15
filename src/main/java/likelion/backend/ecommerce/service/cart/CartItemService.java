package likelion.backend.ecommerce.service.cart;

import jakarta.transaction.Transactional;
import likelion.backend.ecommerce.dto.cart.cartItem.CartItemCreateDTO;
import likelion.backend.ecommerce.dto.cart.cartItem.CartItemResponseDTO;
import likelion.backend.ecommerce.dto.cart.cartItem.CartItemUpdateDTO;
import likelion.backend.ecommerce.entity.cart.CartItem;
import likelion.backend.ecommerce.global.exception.AlreadyExistException;
import likelion.backend.ecommerce.global.exception.NotFoundException;
import likelion.backend.ecommerce.repository.cart.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemResponseDTO createCartItem(CartItemCreateDTO cartItemCreateDTO){
        if(cartItemRepository.findByProductId(cartItemCreateDTO.getProductId()) != true){
            throw new AlreadyExistException("이미 해당 상품은 장바구니에 있습니다.");
        }

        CartItem item = CartItem.builder()
                .productId(cartItemCreateDTO.getProductId())
                .curProductPrice(cartItemCreateDTO.getCurProductPrice())
                .count(cartItemCreateDTO.getCount())
                .build();

        CartItem resCartItem = cartItemRepository.save(item);

        return new CartItemResponseDTO(resCartItem);
    }

}
