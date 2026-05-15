package likelion.backend.ecommerce.dto.cart;

import lombok.Getter;

@Getter
public class CartUpdateDTO {
    private Long productId;
    private Integer curProductPrice;
    private Integer count;
}
