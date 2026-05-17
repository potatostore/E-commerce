package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import likelion.backend.ecommerce.dto.product.ProductCreateDTO;
import likelion.backend.ecommerce.dto.product.ProductResponseDTO;
import likelion.backend.ecommerce.dto.product.ProductUpdateDTO;
import likelion.backend.ecommerce.global.api.ApiResponse;
import likelion.backend.ecommerce.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "상품 추가",
            description = "상품 추가"
    )
    @PostMapping("/post")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> postProduct(
            @Valid @RequestBody ProductCreateDTO productCreateDTO){
        return new ResponseEntity<>(
                ApiResponse.success(
                        "성공적으로 상품을 등록하였습니다.",
                        productService.addProduct(productCreateDTO)
                ),
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "전체 상품 조회",
            description = "등록된 전체 상품 조회"
    )
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getProduct(){
        return new ResponseEntity<>(ApiResponse.success(
                "모든 상품을 조회했습니다.",
                productService.findAllProducts()
        ), HttpStatus.OK);
    }

    @Operation(
            summary = "상품 조회",
            description = "상품 ID를 통해 특정 상품 조회"
    )
    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProduct(@PathVariable Long userId){
        return new ResponseEntity<>(ApiResponse.success(
                userId + "에 해당하는 상품을 조회하였습니다.",
                productService.findProductById(userId)
        ), HttpStatus.OK);
    }

    @Operation(
            summary = "상품 수정",
            description = "상품 ID를 통해 특정 상품 정보 수정"
    )
    @PatchMapping("/patch/{userId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> patchProduct(
            @PathVariable Long userId, @Valid @RequestBody ProductUpdateDTO productUpdateDTO){
        return new ResponseEntity<>(ApiResponse.success(
                userId + "에 해당하는 상품의 정보를 수정하였습니다.",
                productService.editProductById(userId, productUpdateDTO)
        ), HttpStatus.OK);
    }

    @Operation(
            summary = "상품 삭제",
            description = "상품 ID를 통해 특정 상품 삭제"
    )
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> deleteProduct(@PathVariable Long userId){
        return new ResponseEntity<>(ApiResponse.success(
                userId + " : 상품을 삭제하였습니다.",
                productService.deleteProductById(userId)
        ), HttpStatus.OK);
    }
}
