package likelion.backend.ecommerce.dto.product;

import likelion.backend.ecommerce.entity.product.Product;
import likelion.backend.ecommerce.status.ProductStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private ProductStatus productStatus;
    private String description;
    private String productImage;
    private Double rating;
    private Integer likes;
    private List<String> reviews;

    public ProductResponseDTO(Product product){
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.productStatus = product.getProductStatus();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
        this.rating = product.getRating();
        this.likes = product.getLikes();
        this.reviews = product.getReviews();
    }
}
