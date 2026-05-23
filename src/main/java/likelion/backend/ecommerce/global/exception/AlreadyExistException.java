package likelion.backend.ecommerce.global.exception;

public class AlreadyExistException extends GlobalEcommerceException{
    public AlreadyExistException(Errorcode errorcode){
        super(errorcode);
    }
    public AlreadyExistException(Errorcode errorcode, String errorMessage){
        super(errorcode, errorMessage);
    }
}
