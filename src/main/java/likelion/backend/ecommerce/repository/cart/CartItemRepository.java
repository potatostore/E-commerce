package likelion.backend.ecommerce.repository.cart;

import likelion.backend.ecommerce.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean findByProductId(Long productId);
}
