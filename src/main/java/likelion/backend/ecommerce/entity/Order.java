package likelion.backend.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Order {
    @Id
    private String orderId;
    private String userId;
    private String productId;

    private Date orderDate;
    private int count;
    private int totalPrice;
}
