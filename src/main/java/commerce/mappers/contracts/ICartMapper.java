package commerce.mappers.contracts;

import commerce.dto.response.CartResponse;
import commerce.entities.Cart;
import commerce.repositories.CartRepository;

public interface ICartMapper {
    CartResponse entityToResponse(Cart cart);
}
