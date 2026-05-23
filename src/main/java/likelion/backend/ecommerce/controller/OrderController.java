package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import likelion.backend.ecommerce.dto.order.OrderCreateDTO;
import likelion.backend.ecommerce.dto.order.OrderResponseDTO;
import likelion.backend.ecommerce.global.api.ApiResponse;
import likelion.backend.ecommerce.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "주문 생성",
            description = "새로운 주문 생성"
    )
    @PostMapping("/post/{userId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> postOrder(
            @PathVariable Long userId, @Valid @RequestBody OrderCreateDTO orderCreateDTO){
        return ResponseEntity.ok(ApiResponse.success(
                userId + " : 새로운 주문을 생성하였습니다.",
                orderService.createOrder(userId, orderCreateDTO)
        ));
    }

    @Operation(
            summary = "전체 주문 조회",
            description = "전체 주문 조회"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getOrder(){
        return ResponseEntity.ok(ApiResponse.success(
                "모든 주문 정보를 조회하였습니다.",
                orderService.findAllOrders()
        ));
    }

    @Operation(
            summary = "주문 조회",
            description = "ID를 통해 특정 주문 조회"
    )
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.success(
                orderId + " : 주문정보를 조회하였습니다.",
                orderService.findOrderById(orderId)
        ));
    }


    @Operation(
            summary = "주문 정보 삭제",
            description = "ID를 통해 특정 주문 삭제"
    )
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> deleteOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.success(
                orderId + " : 주문정보를 삭제하였습니다.",
                orderService.deleteOrder(orderId)
        ));
    }
}
