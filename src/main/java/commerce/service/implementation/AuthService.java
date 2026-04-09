package commerce.service.implementation;

import commerce.dto.request.LoginRequest;
import commerce.dto.request.RegistrationRequest;
import commerce.entities.Customer;
import commerce.entities.Role;
import commerce.entities.User;
import commerce.exceptions.DuplicateResourceException;
import commerce.repositories.CustomerRepository;
import commerce.repositories.RoleRepository;
import commerce.repositories.UserRepository;
import commerce.security.services.JwtService;
import commerce.service.contract.EmailService;
import commerce.service.contract.IAuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmailService gmailService;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public void registerNewCustomer(RegistrationRequest request) {
        log.info("Creating new user with email: {}", request.email());
        if (userRepository.isEmailExists(request.email()))
            throw new DuplicateResourceException("Email already exists.");
        if (userRepository.isUsernameExists(request.username()))
            throw new DuplicateResourceException("Username already exists.");
        Role role = roleRepository.findRoleByName("CUSTOMER");
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .isEnabled(true)
                .password(encoder.encode(request.password())).roles(Set.of(role)).build();
        Customer customer = Customer.builder().user(user).build();
        User newUser = userRepository.save(user);
        customerRepository.save(customer);
        this.sendWelcomeMail(newUser.getEmail(),newUser.getFirstName());
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
        ));
        return jwtService.generateAccessToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));
    }

    private void sendWelcomeMail(String to, String name) {
        gmailService.sendEmailAsync(to, "Welcome to our E-Commerce platform", String.format("""
                Hello %s,
                Welcome to our E-Commerce platform, we hope you enjoy shopping with us.
                """, name));
    }
}
