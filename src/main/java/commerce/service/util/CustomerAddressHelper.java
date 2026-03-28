package commerce.service.util;

import commerce.entities.Customer;
import commerce.entities.CustomerAddress;
import commerce.entities.User;
import commerce.exceptions.ResourceNotFoundException;
import commerce.repositories.CustomerAddressRepository;
import commerce.security.entities.SecurityUser;
import commerce.service.contract.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerAddressHelper {
    private final ICustomerService customerService;
    private final CustomerAddressRepository addressRepository;

    public Customer getCurrentCustomer() {
        User currentUser = ((SecurityUser)
                SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal()).getUser();
        return customerService.getByUserId(currentUser.getUserId());
    }

    public CustomerAddress getAddressById(Integer addressId) {
        return addressRepository.findByIdAndCustomerId(this.getCurrentCustomer().getId(), addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
    }
}
