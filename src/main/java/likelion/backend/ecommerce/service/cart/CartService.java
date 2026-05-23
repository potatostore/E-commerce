package likelion.backend.ecommerce.service.cart;

import jakarta.transaction.Transactional;
import likelion.backend.ecommerce.dto.cart.CartCreateDTO;
import likelion.backend.ecommerce.dto.cart.cartItem.CartItemCreateDTO;
import likelion.backend.ecommerce.dto.cart.CartResponseDTO;
import likelion.backend.ecommerce.dto.cart.CartUpdateDTO;
import likelion.backend.ecommerce.entity.cart.Cart;
import likelion.backend.ecommerce.entity.cart.CartItem;
import likelion.backend.ecommerce.global.exception.AlreadyExistException;
import likelion.backend.ecommerce.global.exception.Errorcode;
import likelion.backend.ecommerce.global.exception.NotFoundException;
import likelion.backend.ecommerce.global.exception.OutOfStockException;
import likelion.backend.ecommerce.repository.cart.CartRepository;
import likelion.backend.ecommerce.repository.product.ProductRepository;
import likelion.backend.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public CartResponseDTO createCart(CartCreateDTO cartCreateDTO){
        if(cartRepository.existsByUserId(cartCreateDTO.getUserId())){
            throw new AlreadyExistException(Errorcode.CART_ALREADY_EXIST);
        }

        Cart cart = Cart.builder()
                .userId(cartCreateDTO.getUserId()).build();

        Cart saveCart = cartRepository.save(cart);

        return new CartResponseDTO(saveCart);
    }

    @Transactional
    public CartResponseDTO addCart(Long userId, CartItemCreateDTO cartItemCreateDTO){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(Errorcode.CART_NOT_FOUND);
        }

        if(!productRepository.existsById(cartItemCreateDTO.getProductId())){
            throw new NotFoundException(Errorcode.PRODUCT_NOT_FOUND);
        }

        if(!productService.checkingPossibleToBuy(cartItemCreateDTO.getProductId(), cartItemCreateDTO.getQuantity())){
            throw new OutOfStockException(Errorcode.PRODUCT_OUT_OF_STOCK);
        }

        Cart cart = cartRepository.findByUserId(userId);

        CartItem newItem = CartItem.builder()
                .productId(cartItemCreateDTO.getProductId())
                .curProductPrice(cartItemCreateDTO.getCurProductPrice())
                .quantity(cartItemCreateDTO.getQuantity())
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
            throw new NotFoundException(Errorcode.CART_NOT_FOUND);
        }

        Cart cart = cartRepository.findByUserId(userId);

        return new CartResponseDTO(cart);
    }

    @Transactional
    public CartResponseDTO editCart(Long userId, CartUpdateDTO cartUpdateDTO){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(Errorcode.CART_NOT_FOUND);
        }

        Cart cart = cartRepository.findByUserId(userId);

        CartItem targetItem = cart.getCartItemList().stream()
                .filter(item -> item.getProductId().equals(cartUpdateDTO.getProductId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Errorcode.PRODUCT_NOT_FOUND));

        if(cartUpdateDTO.getQuantity() != null){
            targetItem.updateQuantity(cartUpdateDTO.getQuantity());
        }
        if(cartUpdateDTO.getCurProductPrice() != null){
            targetItem.updateCurProductPrice(cartUpdateDTO.getCurProductPrice());
        }

        targetItem.updateTotalProductPrice();
        cart.updateTotalCartPrice();

        return new CartResponseDTO(cart);
    }

    public CartResponseDTO deleteCart(Long userId){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(Errorcode.CART_NOT_FOUND);
        }

        Cart cart = cartRepository.findByUserId(userId);

        cartRepository.delete(cart);
        return new CartResponseDTO(cart);
    }

    public CartResponseDTO deleteCart(Long userId, Long productId){
        if(!cartRepository.existsByUserId(userId)){
            throw new NotFoundException(Errorcode.CART_NOT_FOUND);
        }

        Cart cart = cartRepository.findByUserId(userId);

        CartItem targetItem = cart.getCartItemList().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Errorcode.PRODUCT_NOT_FOUND));

        cart.getCartItemList().remove(targetItem);

        cart.updateTotalCartPrice();

        return new CartResponseDTO(cart);
    }
}
