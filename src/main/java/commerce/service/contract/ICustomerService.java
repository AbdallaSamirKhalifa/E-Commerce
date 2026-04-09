package commerce.service.contract;

import commerce.entities.Customer;

public interface ICustomerService {
    Customer getByUserId(Integer userId);
    Customer fetchWithCartByUserId(Integer userId);

    Customer fetchWithCartAndAddressesByUserId(Integer userId);
}
