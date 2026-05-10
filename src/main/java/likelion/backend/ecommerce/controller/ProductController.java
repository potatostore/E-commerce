package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion.backend.ecommerce.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Operation(
            summary = "상품 추가",
            description = "상품 추가"
    )
    @PostMapping
    public ResponseEntity<Product> postProduct(@RequestBody Product product){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "전체 상품 조회",
            description = "등록된 전체 상품 조회"
    )
    @GetMapping
    public ResponseEntity<List<Product>> getProduct(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "상품 조회",
            description = "상품 ID를 통해 특정 상품 조회"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "상품 수정",
            description = "상품 ID를 통해 특정 상품 정보 수정"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable String id, @RequestBody Product product){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "상품 삭제",
            description = "상품 ID를 통해 특정 상품 삭제"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
