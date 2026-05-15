package likelion.backend.ecommerce.entity.order;

import jakarta.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Enumerated
    private OrderStatus orderStatus;

    @CreatedDate
    @Column(updatable = false)
    private Date orderDate;

    private Integer totalPrice = 0;

    @Builder
    public Order(Long userId, List<OrderItem> orderItemList, Integer count){
        this.userId = userId;
        this.orderItemList = (orderItemList != null) ? orderItemList : new ArrayList<>();

        orderStatus = OrderStatus.ORDER_UNCHECK;

        this.totalPrice = this.orderItemList.stream()
                .mapToInt(item -> item.getTotalPrice() != null ? item.getTotalPrice() : 0)
                .sum();
    }
}
