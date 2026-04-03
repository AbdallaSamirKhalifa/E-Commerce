package commerce.repositories;

import commerce.entities.CartItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {

    @Modifying
    @Query("""
    DELETE FROM CartItem ci WHERE ci.cart.cartId = :cartId
""")
    void deleteAllItemsInBatch(Integer cartId);
}
