package commerce.service.util;

import commerce.entities.Cart;
import commerce.entities.CartItem;
import commerce.entities.Customer;
import commerce.entities.Product;
import commerce.exceptions.ProductUnavailableException;
import commerce.exceptions.ResourceNotFoundException;
import commerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartHelper {
    private final ProductRepository productRepository;
    private final ContextCustomer contextCustomer;

    public Cart createNewCart(Customer customer) {
        return Cart.builder().customer(customer).isLocked(false).build();

    }

    public Customer getContextCustomer() {
        return contextCustomer.getContextCustomer();
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
