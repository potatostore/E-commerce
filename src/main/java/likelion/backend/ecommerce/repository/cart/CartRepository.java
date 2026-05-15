package likelion.backend.ecommerce.repository.cart;

import likelion.backend.ecommerce.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByUserId(Long userId);
    Cart findByUserId(Long userId);
}
