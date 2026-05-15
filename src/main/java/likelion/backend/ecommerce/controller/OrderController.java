package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import likelion.backend.ecommerce.dto.order.OrderCreateDTO;
import likelion.backend.ecommerce.dto.order.OrderResponseDTO;
import likelion.backend.ecommerce.entity.order.Order;
import likelion.backend.ecommerce.global.api.ApiResponse;
import likelion.backend.ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final ProductService productService;

    @Operation(
            summary = "주문 생성",
            description = "새로운 주문 생성"
    )
    @PostMapping("/post")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> postOrder(@Valid @RequestBody OrderCreateDTO createOrder){
        return null;
    }

    @Operation(
            summary = "전체 주문 조회",
            description = "전체 주문 조회"
    )
    @GetMapping
    public ResponseEntity<List<Order>> getOrder(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "주문 조회",
            description = "ID를 통해 특정 주문 조회"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "주문 정보 수정",
            description = "주문 ID를 통해 특정 주문 정보 수정"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Order> patchOrder(@PathVariable String id, @RequestBody Order order){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "주문 정보 삭제",
            description = "ID를 통해 특정 주문 삭제"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
