package commerce.controllers;

import commerce.dto.request.LoginRequest;
import commerce.dto.request.RegistrationRequest;
import commerce.service.contract.IAuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@SecurityRequirements
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerNewCustomer(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        authService.registerNewCustomer(request);


        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
