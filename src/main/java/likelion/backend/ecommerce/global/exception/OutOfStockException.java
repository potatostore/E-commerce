package likelion.backend.ecommerce.global.exception;

public class OutOfStockException extends GlobalEcommerceException {
    public OutOfStockException(Errorcode errorcode){
        super(errorcode);
    }
    public OutOfStockException(Errorcode errorcode, String errorMessage) {
        super(errorcode, errorMessage);
    }
}
