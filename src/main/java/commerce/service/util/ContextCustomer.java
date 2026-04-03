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
        User currentUser = ((SecurityUser)
                SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal()).getUser();
        return customerService.getByUserId(currentUser.getUserId());
    }
}
