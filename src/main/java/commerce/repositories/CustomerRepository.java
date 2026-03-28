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
}
