package likelion.backend.ecommerce.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import likelion.backend.ecommerce.global.constants.TableNames;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = TableNames.orderItemTableName)
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(nullable = false)
    private Long productId;

    private String productName;
    private String productImage;

    @Min(value = 0)
    private Integer curPrice;

    @Min(value = 0)
    private Integer count;

    @Min(value = 0)
    private Integer totalPrice;
}
