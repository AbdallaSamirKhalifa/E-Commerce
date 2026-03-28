package commerce.repositories;

import commerce.entities.CustomerAddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository extends CrudRepository<CustomerAddress, Integer> {
    @Query("""
                SELECT a FROM CustomerAddress a WHERE a.id = :addressId AND a.customer.id = :customerId
            """)
    Optional<CustomerAddress> findByIdAndCustomerId(Integer customerId, Integer addressId);

    @Query("""
                SELECT a FROM CustomerAddress a WHERE a.customer.id = :customerId
            """)
    List<CustomerAddress> findAllByCustomerId(Integer customerId);
}
