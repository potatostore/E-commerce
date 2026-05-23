package likelion.backend.ecommerce.global.exception;

public class NotFoundException extends GlobalEcommerceException{
    public NotFoundException(Errorcode errorcode){
        super(errorcode);
    }
    public NotFoundException(Errorcode errorcode, String errorMessage){
        super(errorcode, errorMessage);
    }
}
