package likelion.backend.ecommerce.dto.product;

import lombok.Getter;

@Getter
public class ProductUpdateDTO {
    private String productName;
    private Integer price;
    private Integer quantity;
    private String description;
    private String productImage;
}
