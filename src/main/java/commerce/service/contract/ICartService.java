package commerce.service.contract;

import commerce.dto.request.AddToCartRequest;
import commerce.dto.request.UpdateCartItemRequest;
import commerce.dto.response.CartResponse;
import commerce.entities.Cart;

public interface ICartService {
    CartResponse addItemToCart(AddToCartRequest request);
    CartResponse getMyCart();
    CartResponse increaseCartItem(UpdateCartItemRequest request);
    CartResponse decreaseCartItem(UpdateCartItemRequest request);
    CartResponse removeItemFromCart(int productId);
    void clearCart(Cart ...carts);

    void saveAndFlush(Cart cart);
}
