package commerce.mappers.contracts;

import commerce.dto.response.OrderResponse;
import commerce.entities.Order;

public interface IOrderMapper {
    OrderResponse entityToResponse(Order order);
}
