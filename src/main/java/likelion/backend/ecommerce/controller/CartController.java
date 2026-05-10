package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion.backend.ecommerce.entity.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Operation(
            summary = "장바구니에 상품 추가",
            description = "장바구니에 상품 추가"
    )
    @PostMapping
    public ResponseEntity<Cart> postCart(@RequestBody Cart cart){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "장바구니 조회",
            description = "장바구니 내 상품 전체 조회"
    )
    @GetMapping
    public ResponseEntity<List<Cart>> getCart(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "장바구니 수정",
            description = "장바구니 내 특정 상품 정보(상품정보 + 개수 등을 조합한 정보) 수정"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Cart> patchCart(@PathVariable String id, @RequestBody Cart cart){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "장바구니 초기화",
            description = "장바구니 내 상품 삭제"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> Delete(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
