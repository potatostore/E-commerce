package likelion.backend.ecommerce.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import likelion.backend.ecommerce.entity.BaseEntity;
import likelion.backend.ecommerce.entity.cart.CartItem;
import likelion.backend.ecommerce.global.constants.TableNames;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = TableNames.orderItemTableName)
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer curProductPrice;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer quantity;

    @Min(value = 0)
    private Integer totalProductPrice;

    @Builder
    public OrderItem(Long productId, Integer curProductPrice, Integer quantity){
        this.productId = productId;
        this.curProductPrice = curProductPrice;
        this.quantity = quantity;

        this.totalProductPrice = (curProductPrice != null && quantity != null) ? curProductPrice * quantity : 0;
    }

    public OrderItem(CartItem item){
        this.productId = item.getProductId();
        this.curProductPrice = item.getCurProductPrice();
        this.quantity = item.getQuantity();
        this.totalProductPrice = item.getTotalProductPrice();
    }

    public void updateTotalProductPrice(){
        this.totalProductPrice = this.quantity * this.curProductPrice;
    }
}
