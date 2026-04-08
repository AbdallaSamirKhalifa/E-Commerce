package commerce.service.contract;

import commerce.dto.request.LoginRequest;
import commerce.dto.request.RegistrationRequest;
import jakarta.servlet.Registration;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthService {
    void registerNewCustomer(RegistrationRequest request);
    String login(LoginRequest loginRequest);
}
