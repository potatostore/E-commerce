package likelion.backend.ecommerce.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalEcommerceException.class)
    public ResponseEntity<?> handleGlobalEcommerceException(GlobalEcommerceException e){
        Map<String, String> body = Map.of(
                "code", e.getErrorcode().getErrorCode(),
                "message", e.getErrorcode().getErrorMessage());

        return ResponseEntity
                .status(e.getErrorcode().getHttpStatus())
                .body(body);
    }
}
