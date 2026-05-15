package likelion.backend.ecommerce.service.cart;

import jakarta.transaction.Transactional;
import likelion.backend.ecommerce.dto.cart.CartCreateDTO;
import likelion.backend.ecommerce.dto.cart.cartItem.CartItemCreateDTO;
import likelion.backend.ecommerce.dto.cart.CartResponseDTO;
import likelion.backend.ecommerce.dto.cart.CartUpdateDTO;
import likelion.backend.ecommerce.entity.cart.Cart;
import likelion.backend.ecommerce.entity.cart.CartItem;
import likelion.backend.ecommerce.global.exception.AlreadyExistException;
import likelion.backend.ecommerce.global.exception.NotFoundException;
import likelion.backend.ecommerce.repository.cart.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    public CartResponseDTO createCart(CartCreateDTO cartCreateDTO){
        if(cartRepository.existsByUserId(cartCreateDTO.getUserId())){
            throw new AlreadyExistException("이미 존재하는 회원은 장바구니입니다.");
        }

        Cart cart = Cart.builder()
                .userId(cartCreateDTO.getUserId()).build();

        Cart saveCart = cartRepository.save(cart);

        return new CartResponseDTO(saveCart);
    }

    @Transactional
    public CartResponseDTO addCart(Long userId, CartItemCreateDTO cartItemCreateDTO){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + " : 회원의 장바구니를 찾을 수 없습니다.");
        }

        Cart cart = cartRepository.findByUserId(userId);

        CartItem newItem = CartItem.builder()
                .productId(cartItemCreateDTO.getProductId())
                .curProductPrice(cartItemCreateDTO.getCurProductPrice())
                .count(cartItemCreateDTO.getCount())
                .build();

        cart.addCartItemInList(newItem);
        cart.updateTotalCartPrice();

        return new CartResponseDTO(cart);
    }

    public List<CartResponseDTO> findAllCarts(){
        List<Cart> carts = cartRepository.findAll();

        if(carts.isEmpty()){
            throw new RuntimeException("아무 장바구니도 존재하지 않습니다.");
        }

        return carts.stream().map(CartResponseDTO::new).toList();
    }

    public CartResponseDTO findCartById(Long userId){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + " : 해당 유저의 장바구니를 조회할 수 없습니다.");
        }

        Cart cart = cartRepository.findByUserId(userId);

        return new CartResponseDTO(cart);
    }

    @Transactional
    public CartResponseDTO editCart(Long userId, CartUpdateDTO cartUpdateDTO){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + "에 해당하는 장바구니를 찾을 수 없습니다.");
        }

        Cart cart = cartRepository.findByUserId(userId);

        CartItem targetItem = cart.getCartItemList().stream()
                .filter(item -> item.getProductId().equals(cartUpdateDTO.getProductId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(cartUpdateDTO.getProductId() + "에 해당되는 상품을 장바구니에서 찾을 수 없습니다."));

        if(cartUpdateDTO.getCount() != null){
            targetItem.setCount(cartUpdateDTO.getCount());
        }
        if(cartUpdateDTO.getCurProductPrice() != null){
            targetItem.setCurProductPrice(cartUpdateDTO.getCurProductPrice());
        }

        targetItem.updateTotalProductPrice();
        cart.updateTotalCartPrice();

        return new CartResponseDTO(cart);
    }

    public CartResponseDTO deleteCart(Long userId){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + "에 해당하는 장바구니를 찾을 수 없습니다.");
        }

        Cart cart = cartRepository.findByUserId(userId);

        cartRepository.delete(cart);
        return new CartResponseDTO(cart);
    }

    public CartResponseDTO deleteCart(Long userId, Long productId){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(userId + "에 해당하는 장바구니를 찾을 수 없습니다.");
        }

        Cart cart = cartRepository.findByUserId(userId);

        CartItem targetItem = cart.getCartItemList().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(productId + "에 해당되는 상품을 장바구니에서 찾을 수 없습니다."));

        cart.getCartItemList().remove(targetItem);

        cart.updateTotalCartPrice();

        return new CartResponseDTO(cart);
    }
}
