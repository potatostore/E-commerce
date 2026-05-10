package likelion.backend.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion.backend.ecommerce.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Operation(
            summary = "유저 정보 생성",
            description = "새로운 유저 생성"
    )
    @PostMapping
    public User postUser(@RequestBody User user){
        return user;
    }

    @Operation(
            summary = "전체 유저 정보 조회",
            description = "전체 유저 정보 조회"
    )
    @GetMapping
    public ResponseEntity<List<User>> getUser(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "유저 정보 조회",
            description = "사용자 ID를 통해 사용자 정보 조회"
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "유저 정보 수정",
            description = "사용자 ID를 통해 사용자 정보 수정"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable String id, @RequestBody User user){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "유저 정보 삭제",
            description = "유저 id를 통해 유저 정보 삭제"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
