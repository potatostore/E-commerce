package likelion.backend.ecommerce.repository;

import likelion.backend.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByProductName(String productName);
}
