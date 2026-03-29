package commerce.mappers.contracts;

import commerce.dto.response.CartItemResponse;
import commerce.entities.CartItem;

public interface ICartItemMapper {
    CartItemResponse entityToResponse(CartItem cartItem);
    CartItem responseToEntity(CartItemResponse cartItemResponse);
}
