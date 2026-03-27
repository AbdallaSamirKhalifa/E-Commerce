package commerce.service.contract;

import commerce.dto.request.RegistrationRequest;
import jakarta.servlet.Registration;

public interface IAuthService {
    void registerNewCustomer(RegistrationRequest request);
}
