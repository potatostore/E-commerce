package likelion.backend.ecommerce.repository.product;

import likelion.backend.ecommerce.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String productName);
}
