package commerce.service.implementation;

import commerce.dto.request.AddToCartRequest;
import commerce.dto.response.CartResponse;
import commerce.entities.*;
import commerce.exceptions.LockedCartException;
import commerce.exceptions.ResourceNotFoundException;
import commerce.mappers.implementation.CartMapper;
import commerce.repositories.CartRepository;
import commerce.service.contract.ICartService;
import commerce.service.util.CartHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartHelper helper;
    private final CartMapper mapper;

    @Override
    @Transactional
    public CartResponse addItemToCart(AddToCartRequest request) {

        Customer customer= helper.getContextCustomer();

        Cart cart=cartRepository.findWithItemsAndProductInfoByCustomerId(customer.getId())
                .orElseGet(()->helper.createNewCart(customer));

        if (cart.getIsLocked())
            throw new LockedCartException();

        Optional<CartItem> existingItem=helper.validateItemExistenceInCart(request.productId(),cart);

        if(existingItem.isPresent())
            return this.UpdateCartItem(cart,existingItem.get(),request.quantity());

        Product product= helper.fetchValidateProductAvailabilityById(request.productId());


        CartItem newItem = CartItem.builder().cart(cart).product(product).quantity(request.quantity()).build();
        cart.addCartItem(newItem);

        return mapper.entityToResponse(cart);
    }

    @Override
    public CartResponse getMyCart() {
        Customer customer=helper.getContextCustomer();

        return cartRepository.findWithItemsAndProductInfoByCustomerId(customer.getId()).
                map(mapper::entityToResponse).
                orElseThrow(()-> new ResourceNotFoundException("Cart"));
    }

    private CartResponse UpdateCartItem(Cart cart, CartItem cartItem, Integer quantityToAdd){
        helper.validateProductAvailability(cartItem.getProduct().getIsAvailable(), cartItem.getProduct().getName());
        cartItem.setQuantity(cartItem.getQuantity()+quantityToAdd);
        return mapper.entityToResponse(cart);
    }
}
