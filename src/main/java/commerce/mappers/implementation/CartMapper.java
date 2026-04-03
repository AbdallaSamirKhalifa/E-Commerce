package commerce.mappers.implementation;

import commerce.dto.response.CartResponse;
import commerce.entities.Cart;
import commerce.entities.CartItem;
import commerce.mappers.contracts.ICartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CartMapper implements ICartMapper {
    private final CartItemMapper cartItemMapper;
    @Override
    public CartResponse entityToResponse(Cart cart) {
        return new CartResponse(
                cart.getCartItems().stream().map(cartItemMapper::entityToResponse).toList(),
                cart.getCartItems().stream().map(item->item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).
                        reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
