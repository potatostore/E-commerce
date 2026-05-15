package likelion.backend.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import likelion.backend.ecommerce.entity.product.Product;
import lombok.Getter;

@Getter
public class ProductCreateDTO {
    @NotBlank(message = "상품 이름 입력은 필수입니다.")
    private String productName;

    @NotNull(message = "상품 가격 입력은 필수입니다.")
    @Min(value = 0, message = "0원 이상으로 설정해주세요.")
    private Integer price;

    @NotNull(message = "상품 수량 입력은 필수입니다.")
    @Min(value = 1, message = "상품은 1개 이상으로 설정해주세요.")
    private Integer quantity;

    @NotBlank(message = "상품 설명을 기입해주세요.")
    private String description;

    @NotBlank(message = "상품 이미지를 추가해주세요")
    private String productImage;

    public ProductCreateDTO(Product product){
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
    }
}
