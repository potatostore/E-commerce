package likelion.backend.ecommerce.service;

import likelion.backend.ecommerce.dto.ProductCreateDTO;
import likelion.backend.ecommerce.entity.Product;
import likelion.backend.ecommerce.dto.ProductResponseDTO;
import likelion.backend.ecommerce.global.api.ApiResponse;
import likelion.backend.ecommerce.global.exception.AlreadyExistException;
import likelion.backend.ecommerce.global.exception.GlobalExceptionHandler;
import likelion.backend.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ProductResponseDTO addProduct(ProductCreateDTO productCreateDTO){
        if(productRepository.existsByProductName(productCreateDTO.getProductName())){
            throw new AlreadyExistException(productCreateDTO.getProductName() + "는 이미 존재하는 상품입니다. 다른 이름으로 시도해 주세요");
        }

        Product product = Product.builder()
                .productName(productCreateDTO.getProductName())
                .price(productCreateDTO.getPrice())
                .quantity(productCreateDTO.getQuantity())
                .productImage(productCreateDTO.getProductImage())
                .description(productCreateDTO.getDescription())
                .build();

        Product saveProduct = productRepository.save(product);

        return new ProductResponseDTO(saveProduct);
    }

    public ResponseEntity<Product> getAllProducts(){
        return (productRepository.find)
    }

    public ResponseEntity<Product> findProductById(String productId){
        return (productRepository.findById(productId).equals(Optional.empty())) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(productRepository.findById(productId).get(), HttpStatus.OK);
    }

    public ResponseEntity<Product> deleteProductById(String productId){
        return (productRepository.findById(productId).equals(Optional.empty())) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(productRepository.deleteById(productId), HttpStatus.OK);
    }
}
