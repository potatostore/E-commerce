package likelion.backend.ecommerce.entity;

import jakarta.persistence.*;
import likelion.backend.ecommerce.global.constants.TableNames;
import likelion.backend.ecommerce.status.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = TableNames.productTableName)
public class Product {
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
}