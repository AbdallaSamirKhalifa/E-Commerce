package commerce.mappers.implementation;

import commerce.dto.response.OrderResponse;
import commerce.entities.Order;
import commerce.mappers.contracts.IOrderItemMapper;
import commerce.mappers.contracts.IOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper implements IOrderMapper {
    private final IOrderItemMapper itemMapper;
    @Override
    public OrderResponse entityToResponse(Order order) {
        return new OrderResponse(order.getId(),
                order.getOrderDate(),order.getTotalAmount(),
                order.getNote(),order.getOrderItems()
                .stream().map(itemMapper::entityToResponse).toList());
    }




}
