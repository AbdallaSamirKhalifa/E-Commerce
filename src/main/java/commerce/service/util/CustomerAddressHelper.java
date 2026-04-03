package commerce.service.util;

import commerce.entities.Customer;
import commerce.entities.CustomerAddress;
import commerce.exceptions.ResourceNotFoundException;
import commerce.repositories.CustomerAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerAddressHelper {
    private final ContextCustomer contextCustomer;
    private final CustomerAddressRepository addressRepository;

    public Customer getCurrentCustomer() {
        return contextCustomer.getContextCustomer();
    }

    public CustomerAddress getAddressById(Integer addressId) {
        return addressRepository.findByIdAndCustomerId(this.getCurrentCustomer().getId(), addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
    }
}
