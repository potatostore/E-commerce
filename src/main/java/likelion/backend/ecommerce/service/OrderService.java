package likelion.backend.ecommerce.service;

import likelion.backend.ecommerce.dto.order.OrderCreateDTO;
import likelion.backend.ecommerce.dto.order.OrderResponseDTO;
import likelion.backend.ecommerce.repository.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderResponseDTO addOrder(OrderCreateDTO createOrder){
        return null;
    }
}
