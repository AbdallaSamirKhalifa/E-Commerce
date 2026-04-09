package commerce.repositories;

import commerce.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    @Query("""
    SELECT c FROM Customer c WHERE c.user.id = :userId
""")
    Optional<Customer> findByUserId(Integer userId);

    @Query("""
        SELECT c FROM Customer c
        LEFT JOIN FETCH c.cart cr
        LEFT JOIN FETCH cr.cartItems ci
        LEFT JOIN FETCH ci.product p
        WHERE c.user.id = :userId
    """)
    Optional<Customer> fetchWithCartAndCartItemsAndProductInfoByUserId(Integer userId);

    @Query("""
        SELECT c FROM Customer c
        LEFT JOIN FETCH c.addresses
        LEFT JOIN FETCH c.cart cr
        LEFT JOIN FETCH cr.cartItems ci
        LEFT JOIN FETCH ci.product p
        WHERE c.user.id = :userId
    """)
    Optional<Customer> fetchWithAddressesAndCartAndCartItemsAndProductInfoByUserId(Integer userId);
}
