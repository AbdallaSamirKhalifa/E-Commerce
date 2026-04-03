package commerce.mappers.implementation;

import commerce.dto.response.OrderItemResponse;
import commerce.entities.OrderItem;
import commerce.mappers.contracts.IOrderItemMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper implements IOrderItemMapper {
    @Override
    public OrderItemResponse entityToResponse(OrderItem item) {
        return new OrderItemResponse(item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubtotal());
    }
}
