package likelion.backend.ecommerce.entity.payment;

import jakarta.persistence.*;
import likelion.backend.ecommerce.entity.BaseEntity;
import likelion.backend.ecommerce.global.constants.TableNames;
import likelion.backend.ecommerce.status.payment.PaymentMethod;
import likelion.backend.ecommerce.status.payment.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = TableNames.paymentTableName)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String paymentToken;

    @Column(nullable = false)
    private Integer payAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // CARD, BANK_TRANSFER 등 (Enum)

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // READY, COMPLETED, FAILED (Enum)

    private Date paidAt;

    @Builder
    public Payment(Long orderId, Long userId, String paymentToken, Integer payAmount, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.paymentToken = paymentToken;
        this.payAmount = payAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = PaymentStatus.PREPARE_PRODUCT; // 우선은 생성 시 바로 완료로 가정
        this.paidAt = new Date();
    }
}