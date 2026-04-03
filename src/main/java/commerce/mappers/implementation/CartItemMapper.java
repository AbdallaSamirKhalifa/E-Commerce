package commerce.mappers.implementation;

import commerce.dto.response.CartItemResponse;
import commerce.entities.CartItem;
import commerce.mappers.contracts.ICartItemMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartItemMapper implements ICartItemMapper {
    @Override
    public CartItemResponse entityToResponse(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice(), cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
    }

    @Override
    public CartItem responseToEntity(CartItemResponse cartItemResponse) {
        return CartItem.builder().
                quantity(cartItemResponse.quantity()).build();
    }
}
