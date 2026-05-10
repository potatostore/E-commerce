package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion.backend.ecommerce.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Operation(
            summary = "주문 생성",
            description = "새로운 주문 생성"
    )
    @PostMapping
    public ResponseEntity<Order> postOrder(@RequestBody Order order){
        return new ResponseEntity<>(HttpStatus.OK);
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
