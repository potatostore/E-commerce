package likelion.backend.ecommerce.repository.order;

import likelion.backend.ecommerce.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public boolean existsByUserId(Long userId);
    public Order findByUserId(Long userId);
}
