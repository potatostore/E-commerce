package likelion.backend.ecommerce.entity.order;

import jakarta.persistence.*;
import likelion.backend.ecommerce.entity.cart.Cart;
import likelion.backend.ecommerce.global.constants.TableNames;
import likelion.backend.ecommerce.status.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = TableNames.orderTableName)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long cartId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItemList;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    @Column(updatable = false)
    private Date orderDate;

    private Integer totalOrderPrice;

    @Builder
    public Order(Long userId, Cart cart){
        this.userId = userId;
        this.cartId = cart.getCartId();
        this.orderStatus = OrderStatus.ORDER_UNCHECK;
        this.orderDate = new Date();

        this.orderItemList = new ArrayList<>();
        List<OrderItem> items = cart.getCartItemList().stream().map(OrderItem::new).toList();
        this.orderItemList.addAll(items);

        updateTotalOrderPrice();
    }

    public void updateTotalOrderPrice(){
        this.totalOrderPrice = this.orderItemList.stream()
                .mapToInt(OrderItem::getTotalProductPrice).sum();
    }
}
