package commerce.mappers.implementation;

import commerce.dto.response.CartItemResponse;
import commerce.entities.CartItem;
import commerce.mappers.contracts.ICartItemMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper implements ICartItemMapper {
    @Override
    public CartItemResponse entityToResponse(CartItem cartItem) {
        return new CartItemResponse(cartItem.getCartItemId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(), cartItem.getSubtotal());
    }

    @Override
    public CartItem responseToEntity(CartItemResponse cartItemResponse) {
        return CartItem.builder().
                cartItemId(cartItemResponse.cartItemId()).
                quantity(cartItemResponse.quantity()).
                subtotal(cartItemResponse.subTotal()).build();
    }
}
