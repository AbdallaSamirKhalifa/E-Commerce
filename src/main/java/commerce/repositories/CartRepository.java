package commerce.repositories;

import commerce.entities.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    @Query("""
            
                        SELECT c FROM Cart c
                    LEFT JOIN FETCH c.cartItems ci
                    LEFT JOIN FETCH ci.product
                    WHERE c.customer.id = :customerId
            """)
    Optional<Cart> findWithItemsAndProductInfoByCustomerId(Integer customerId);

}
