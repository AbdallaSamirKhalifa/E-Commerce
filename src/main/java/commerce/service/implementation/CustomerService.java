package commerce.service.implementation;

import commerce.entities.Customer;
import commerce.exceptions.ResourceNotFoundException;
import commerce.repositories.CustomerRepository;
import commerce.service.contract.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer getByUserId(Integer userId) {
        return customerRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Customer"));
    }
    @Override
    public Customer fetchWithCartByUserId(Integer userId) {
        return customerRepository.fetchWithCartAndCartItemsAndProductInfoByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer"));
    }
}
