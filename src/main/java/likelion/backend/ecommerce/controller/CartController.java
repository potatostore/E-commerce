package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import likelion.backend.ecommerce.dto.cart.CartCreateDTO;
import likelion.backend.ecommerce.dto.cart.cartItem.CartItemCreateDTO;
import likelion.backend.ecommerce.dto.cart.CartResponseDTO;
import likelion.backend.ecommerce.dto.cart.CartUpdateDTO;
import likelion.backend.ecommerce.global.api.ApiResponse;
import likelion.backend.ecommerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @Operation(
            summary = "장바구니 생성",
            description = "회원가입시 장바구니 생성"
    )
    @PostMapping("/post")
    public ResponseEntity<ApiResponse<CartResponseDTO>> postCart(
            @Valid @RequestBody CartCreateDTO cartCreateDTO){
        return ResponseEntity.ok(ApiResponse.success(
                "장바구니 생성에 성공하였습니다.",
                cartService.createCart(cartCreateDTO)
        ));
    }

    @Operation(
            summary = "장바구니에 상품 추가",
            description = "회원이 장바구니에 상품 추가"
    )
    @PostMapping("/post/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> postCart(
            @PathVariable Long userId, @Valid @RequestBody CartItemCreateDTO cartItemCreateDTO){
        return ResponseEntity.ok(ApiResponse.success(
                userId + "회원의 장바구니에 상품을 성공적으로 추가하였습니다.",
                cartService.addCart(userId, cartItemCreateDTO)
        ));
    }

    @Operation(
            summary = "모든 유저 장바구니 조회",
            description = "모든 유저의 장바구니 조회"
    )
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<CartResponseDTO>>> getCart(){
        return ResponseEntity.ok(ApiResponse.success(
                "모든 장바구니를 조회하였습니다.",
                cartService.findAllCarts()
        ));
    }

    @Operation(
            summary = "유저 장바구니 조회",
            description = "특정 유저의 장바구니 내 상품 전체 조회"
    )
    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCart(@PathVariable Long userId){
        return ResponseEntity.ok(ApiResponse.success(
                userId + "회원의 모든 장바구니를 조회하였습니다.",
                cartService.findCartById(userId)
        ));
    }

    @Operation(
            summary = "장바구니 수정",
            description = "장바구니 내 특정 상품 정보(상품정보 + 개수 등을 조합한 정보) 수정"
    )
    @PatchMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> patchCart(
            @PathVariable Long id, @Valid @RequestBody CartUpdateDTO cartUpdateDTO){
        return ResponseEntity.ok(ApiResponse.success(
                id + "에 해당되는 장바구니를 조회하였습니다.",
                cartService.editCart(id, cartUpdateDTO)
        ));
    }

    @Operation(
            summary = "장바구니 삭제",
            description = "유저 삭제시 장바구니 테이블도 같이 삭제"
    )
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> deleteCart(@PathVariable Long userId){
        return ResponseEntity.ok(ApiResponse.success(
                userId + "에 해당되는 장바구니를 삭제하였습니다.",
                cartService.deleteCart(userId)
        ));
    }

    @Operation(
            summary = "장바구니 내 상품 삭제",
            description = "장바구니 내 특정 상품을 완전히 삭제"
    )
    @Transactional
    @DeleteMapping("/delete/{userId}/{productId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> deleteCartItemInCart(
            @PathVariable Long userId, @PathVariable Long productId){
        return ResponseEntity.ok(ApiResponse.success(
                userId + "의 장바구니 내 " +  productId + " 상품을 삭제하였습니다.",
                cartService.deleteCart(userId, productId)
        ));
    }
}
