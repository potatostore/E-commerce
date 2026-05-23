package likelion.backend.ecommerce.service.product;

import jakarta.transaction.Transactional;
import likelion.backend.ecommerce.dto.product.ProductCreateDTO;
import likelion.backend.ecommerce.dto.product.ProductUpdateDTO;
import likelion.backend.ecommerce.entity.product.Product;
import likelion.backend.ecommerce.dto.product.ProductResponseDTO;
import likelion.backend.ecommerce.global.exception.AlreadyExistException;
import likelion.backend.ecommerce.global.exception.Errorcode;
import likelion.backend.ecommerce.global.exception.GlobalExceptionHandler;
import likelion.backend.ecommerce.global.exception.NotFoundException;
import likelion.backend.ecommerce.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ProductResponseDTO addProduct(ProductCreateDTO productCreateDTO){
        if(productRepository.existsByProductName(productCreateDTO.getProductName())){
            throw new AlreadyExistException(Errorcode.PRODUCT_ALREADY_EXIST);
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
            throw new NotFoundException(Errorcode.PRODUCT_NOT_FOUND);
        }

        return products.stream()
                .map(ProductResponseDTO::new)
                .toList();
    }

    public ProductResponseDTO findProductById(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Errorcode.PRODUCT_NOT_FOUND));

        return new ProductResponseDTO(product);
    }

    @Transactional
    public ProductResponseDTO editProductById(Long productId, ProductUpdateDTO productUpdateDTO){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Errorcode.PRODUCT_NOT_FOUND));

        product.updateProduct(productUpdateDTO);

        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO deleteProductById(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Errorcode.PRODUCT_NOT_FOUND));

        productRepository.deleteById(productId);

        return new ProductResponseDTO(product);
    }

    public boolean checkingPossibleToBuy(Long productId, Integer quantity){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(Errorcode.PRODUCT_NOT_FOUND));

        if(product.getQuantity() < quantity){
            return false;
        }
        return true;
    }
}
