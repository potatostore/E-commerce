package likelion.backend.ecommerce.entity.cart;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import likelion.backend.ecommerce.dto.cart.cartItem.CartItemResponseDTO;
import likelion.backend.ecommerce.global.constants.TableNames;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableNames.cartItemTableName)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

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
    public CartItem(Long productId, Integer curProductPrice, Integer count){
        this.productId = productId;
        this.curProductPrice = curProductPrice;
        this.count = count;

        this.totalProductPrice = (curProductPrice != null && count != null) ? curProductPrice * count : 0;
    }

    public CartItem(CartItemResponseDTO cartItemResponseDTO){
        this.productId = cartItemResponseDTO.getProductId();
        this.curProductPrice = cartItemResponseDTO.getCurProductPrice();;
        this.count = cartItemResponseDTO.getCount();
        this.totalProductPrice = cartItemResponseDTO.getTotalPrice();
    }

    public void updateTotalProductPrice(){
        this.totalProductPrice = count * curProductPrice;
    }
}
