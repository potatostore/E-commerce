package likelion.backend.ecommerce.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import likelion.backend.ecommerce.entity.cart.CartItem;
import likelion.backend.ecommerce.global.constants.TableNames;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableNames.orderItemTableName)
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer curProductPrice;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer count;

    @Min(value = 0)
    private Integer totalProductPrice;

    @Builder
    public OrderItem(Long productId, Integer curProductPrice, Integer count){
        this.productId = productId;
        this.curProductPrice = curProductPrice;
        this.count = count;

        this.totalProductPrice = (curProductPrice != null && count != null) ? curProductPrice * count : 0;
    }

    public OrderItem(CartItem item){
        this.productId = item.getProductId();
        this.curProductPrice = item.getCurProductPrice();
        this.count = item.getCount();
        this.totalProductPrice = item.getTotalProductPrice();
    }

    public void updateTotalProductPrice(){
        this.totalProductPrice = this.count * this.curProductPrice;
    }
}
