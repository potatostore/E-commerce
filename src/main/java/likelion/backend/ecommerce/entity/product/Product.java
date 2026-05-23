package likelion.backend.ecommerce.entity.product;

import jakarta.persistence.*;
import likelion.backend.ecommerce.dto.product.ProductUpdateDTO;
import likelion.backend.ecommerce.entity.BaseEntity;
import likelion.backend.ecommerce.global.constants.TableNames;
import likelion.backend.ecommerce.status.product.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = TableNames.productTableName)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated
    private ProductStatus productStatus;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String productImage;

    private Double rating;
    private Integer likes;

    @ElementCollection
    @CollectionTable(
            name = "product_reviews",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(nullable = false)
    private List<String> reviews;

    @Builder
    public Product(String productName, Integer price, Integer quantity, String description, String productImage){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.productImage = productImage;

        if(quantity <= 10){
            this.productStatus = ProductStatus.ALMOSTSOLDOUT;
        }
        else{
            this.productStatus = ProductStatus.OK;
        }
    }

    public void updateProduct(ProductUpdateDTO productUpdateDTO){
        if(productUpdateDTO.getProductName() != null){
            this.productName = productUpdateDTO.getProductName();
        }
        if(productUpdateDTO.getProductImage() != null){
            this.productImage = productUpdateDTO.getProductImage();
        }
        if(productUpdateDTO.getDescription() != null){
            this.description = productUpdateDTO.getDescription();
        }
        if(productUpdateDTO.getPrice() != null){
            this.price = productUpdateDTO.getPrice();
        }
        if(productUpdateDTO.getQuantity() != null){
            this.quantity = productUpdateDTO.getQuantity();
        }

        this.updateProductStatus();
    }

    public void updateProductStatus(){
        if(this.quantity > 10){
            this.productStatus = ProductStatus.OK;
        }
        if(this.quantity <= 10){
            this.productStatus = ProductStatus.ALMOSTSOLDOUT;
        }
        if(this.quantity == 0){
            this.productStatus = ProductStatus.SOLDOUT;
        }
    }

    public void updateProductQuantity(Integer quantity){
        this.quantity += quantity;
    }
}