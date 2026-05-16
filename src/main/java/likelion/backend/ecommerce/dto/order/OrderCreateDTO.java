package likelion.backend.ecommerce.dto.order;

import lombok.Getter;

@Getter
public class OrderCreateDTO {
    private Long userId;
    private String paymentToken;
}
