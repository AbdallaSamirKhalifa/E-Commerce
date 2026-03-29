package commerce.service.util;

import commerce.entities.*;
import commerce.exceptions.ProductUnavailableException;
import commerce.exceptions.ResourceNotFoundException;
import commerce.repositories.CustomerRepository;
import commerce.repositories.ProductRepository;
import commerce.security.entities.SecurityUser;
import commerce.service.contract.ICustomerAddressService;
import commerce.service.contract.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartHelper {
    private final ProductRepository productRepository;
    private final ICustomerService customerService;

    public Cart createNewCart(Customer customer) {
        return Cart.builder().customer(customer).isLocked(false).build();

    }
    public Customer getContextCustomer(){
        User currentUser = ((SecurityUser)
                SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal()).getUser();
       return customerService.getByUserId(currentUser.getUserId());
    }
    public Optional<CartItem> validateItemExistenceInCart(int productId, Cart cart) {
        return cart.getCartItems()
                .stream().filter(item -> item.getProduct().
                        getId().equals(productId)).
                findFirst();
    }

    public Product fetchValidateProductAvailabilityById(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product", productId));
        validateProductAvailability(product.getIsAvailable(), product.getName());
        return product;
    }

    public void validateProductAvailability(boolean isAvailable, String productName) {
        if (!isAvailable)
            throw new ProductUnavailableException(productName);

    }
}
