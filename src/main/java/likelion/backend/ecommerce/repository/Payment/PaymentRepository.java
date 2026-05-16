package likelion.backend.ecommerce.repository.Payment;

import likelion.backend.ecommerce.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
