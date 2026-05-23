package likelion.backend.ecommerce.global.exception;

import lombok.Getter;

@Getter
public class GlobalEcommerceException extends RuntimeException{
    private final Errorcode errorcode;

    public GlobalEcommerceException(Errorcode errorcode){
        super(errorcode.getErrorMessage());
        this.errorcode = errorcode;
    }

    public GlobalEcommerceException(Errorcode errorcode, String errorMessage){
        super(errorMessage);
        this.errorcode = errorcode;
    }
}
