package likelion.backend.ecommerce.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Errorcode {
    //Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "해당 상품을 찾을 수 없습니다."),
    PRODUCT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "P002", "이미 해당 상품과 동일한 정보의 상품이 존재합니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "P003", "상품의 수량이 부족합니다."),

    //Cart
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "장바구니를 찾을 수 없습니다."),
    CART_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "C002", "회원의 장바구니가 이미 존재합니다."),
    CART_EMPTY(HttpStatus.NO_CONTENT, "C003", "장바구니에 아무 상품도 없습니다."),

    //Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "주문정보를 조회할 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

}
