package commerce.service.implementation;

import commerce.dto.request.AddToCartRequest;
import commerce.dto.request.UpdateCartItemRequest;
import commerce.dto.response.CartResponse;
import commerce.entities.*;
import commerce.exceptions.InvalidQuantityException;
import commerce.exceptions.LockedCartException;
import commerce.exceptions.ResourceNotFoundException;
import commerce.mappers.implementation.CartMapper;
import commerce.repositories.CartItemRepository;
import commerce.repositories.CartRepository;
import commerce.service.contract.ICartService;
import commerce.service.util.CartHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartItemRepository cartItemRepository;
    private final CartHelper helper;
    private final CartMapper mapper;

    @Override
    @Transactional
    public CartResponse addItemToCart(AddToCartRequest request) {

        Customer customer= helper.fetchContextCustomerWithCart();

        if (customer.getCart()==null)
        {
            Cart crt= helper.createNewCart(customer);
            customer.setCart(crt);
        }
        Cart cart=customer.getCart();

        if (cart.getIsLocked())
            throw new LockedCartException();

        if (cart.getCartItems()==null)
            cart.setCartItems(new HashSet<>());

        Optional<CartItem> existingItem=helper.findValidateItemExistenceInCartByProductId(request.productId(),cart);

        if(existingItem.isPresent())
            return this.updateCartItem(cart,existingItem.get(),request.quantity());

        Product product= helper.fetchValidateProductAvailabilityById(request.productId());


        CartItem newItem = CartItem.builder().cart(cart).product(product).quantity(request.quantity()).build();
        cart.addCartItem(newItem);

        return mapper.entityToResponse(cart);
    }

    @Override
    public CartResponse getMyCart() {
        Customer customer=helper.fetchContextCustomerWithCart();

        if (customer.getCart()==null)
            throw new ResourceNotFoundException("Cart");
        return mapper.entityToResponse(customer.getCart());
//        return cartRepository.findWithItemsAndProductInfoByCustomerId(customer.getId()).
//                map(mapper::entityToResponse).
//                orElseThrow(()-> new );
    }

    @Transactional
    @Override
    public CartResponse increaseCartItem(UpdateCartItemRequest request) {
        Customer customer =helper.fetchContextCustomerWithCart();

        Cart cart=customer.getCart();
        helper.validateCartEligibility(cart);

        CartItem item=
        helper.findValidateItemExistenceInCartByProductId(request.productId(), cart).orElseThrow(()->new ResourceNotFoundException("Item"));
        helper.validateProductAvailability(item.getProduct().getIsAvailable(),item.getProduct().getName());
        item.setQuantity(item.getQuantity()+request.quantity());

        return mapper.entityToResponse(cart);
    }

    @Transactional
    public CartResponse decreaseCartItem(UpdateCartItemRequest request){
        Customer customer=helper.fetchContextCustomerWithCart();
        Cart cart=customer.getCart();

        helper.validateCartEligibility(cart);

        CartItem item=
                helper.findValidateItemExistenceInCartByProductId(request.productId(), cart).orElseThrow(()->new ResourceNotFoundException("Item"));
        if (request.quantity()>item.getQuantity())
            throw new InvalidQuantityException("Invalid quantity, if you want to remove the item you can use the remove button.");

        item.setQuantity(item.getQuantity()-request.quantity());
        return mapper.entityToResponse(cart);
    }

    @Transactional
    public CartResponse removeItemFromCart(int productId){
        Customer customer =helper.fetchContextCustomerWithCart();
        Cart cart=customer.getCart();

        helper.validateCartEligibility(cart);
        CartItem item=helper.findValidateItemExistenceInCartByProductId(productId,cart)
                .orElseThrow(()->new ResourceNotFoundException("Item"));
        cart.getCartItems().remove(item);
        return mapper.entityToResponse(cart);
    }

    @Transactional
    public void clearCart(){
        Customer customer= helper.fetchContextCustomerWithCart();
        Cart cart=customer.getCart();
        if (cart==null)
            throw new ResourceNotFoundException("Cart");
        cartItemRepository.deleteAllItemsInBatch(cart.getCartId());
        cart.setIsLocked(false);
    }

    private CartResponse updateCartItem(Cart cart, CartItem cartItem, Integer quantityToAdd){
        helper.validateProductAvailability(cartItem.getProduct().getIsAvailable(), cartItem.getProduct().getName());
        cartItem.setQuantity(cartItem.getQuantity()+quantityToAdd);
        return mapper.entityToResponse(cart);
    }
}
