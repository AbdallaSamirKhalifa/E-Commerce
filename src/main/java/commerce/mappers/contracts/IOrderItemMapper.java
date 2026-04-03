package commerce.mappers.contracts;

import commerce.dto.response.OrderItemResponse;
import commerce.entities.OrderItem;

public interface IOrderItemMapper {
    OrderItemResponse entityToResponse(OrderItem item);
}
