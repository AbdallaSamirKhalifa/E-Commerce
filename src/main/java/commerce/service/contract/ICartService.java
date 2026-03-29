package commerce.service.contract;

import commerce.dto.request.AddToCartRequest;
import commerce.dto.response.CartResponse;

public interface ICartService {
    CartResponse addItemToCart(AddToCartRequest request);
    CartResponse getMyCart();
}
