package likelion.backend.ecommerce.service.product;

import jakarta.transaction.Transactional;
import likelion.backend.ecommerce.dto.product.ProductCreateDTO;
import likelion.backend.ecommerce.dto.product.ProductUpdateDTO;
import likelion.backend.ecommerce.entity.product.Product;
import likelion.backend.ecommerce.dto.product.ProductResponseDTO;
import likelion.backend.ecommerce.global.exception.AlreadyExistException;
import likelion.backend.ecommerce.global.exception.GlobalExceptionHandler;
import likelion.backend.ecommerce.global.exception.NotFoundException;
import likelion.backend.ecommerce.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ProductResponseDTO> findAllProducts(){
        List<Product> products = productRepository.findAll();

        if(products.isEmpty()){
            throw new NotFoundException("상품이 존재하지 않습니다.");
        }

        return products.stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    public ProductResponseDTO findProductById(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(productId + " : 해당 상품이 존재하지 않습니다."));

        return new ProductResponseDTO(product);
    }

    @Transactional
    public ProductResponseDTO editProductById(Long productId, ProductUpdateDTO productUpdateDTO){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(productId + " : 해당 상품이 존재하지 않습니다."));

        if(productUpdateDTO.getProductName() != null){
            product.setProductName(productUpdateDTO.getProductName());
        }
        if(productUpdateDTO.getProductImage() != null){
            product.setProductImage(productUpdateDTO.getProductImage());
        }
        if(productUpdateDTO.getDescription() != null){
            product.setDescription(productUpdateDTO.getDescription());
        }
        if(productUpdateDTO.getPrice() != null){
            product.setPrice(productUpdateDTO.getPrice());
        }
        if(productUpdateDTO.getQuantity() != null){
            product.setQuantity(productUpdateDTO.getQuantity());
        }

        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO deleteProductById(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(productId + "에 해당되는 상품을 찾을 수 없습니다."));

        productRepository.deleteById(productId);

        return new ProductResponseDTO(product);
    }
}
