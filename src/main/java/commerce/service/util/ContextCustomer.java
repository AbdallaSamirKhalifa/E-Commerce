package commerce.service.util;

import commerce.entities.Customer;
import commerce.entities.User;
import commerce.security.entities.SecurityUser;
import commerce.service.contract.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContextCustomer {
    private final ICustomerService customerService;

    public Customer getContextCustomer() {
        User currentUser = getContextUser();
        return customerService.getByUserId(currentUser.getUserId());
    }
    public Customer getContextCustomerWithCartInfo() {
        User currentUser = getContextUser();
        return customerService.fetchWithCartByUserId(currentUser.getUserId());
    }
    public Customer getContextCustomerWithAddressesAndCartInfo(){
        User currentUser = getContextUser();
        return customerService.fetchWithCartAndAddressesByUserId(currentUser.getUserId());
    }
    private User getContextUser(){
        return ((SecurityUser)
                SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal()).getUser();
    }
}
