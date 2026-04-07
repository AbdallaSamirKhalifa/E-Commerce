package commerce.controllers;

import commerce.dto.request.RegistrationRequest;
import commerce.service.contract.IAuthService;
import commerce.service.implementation.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.MessageCodeFormatter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerNewCustomer(@RequestBody @Valid RegistrationRequest request)throws MessagingException {
        authService.registerNewCustomer(request);


        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

@GetMapping("/login")
    public ResponseEntity<String> login(Authentication authentication){
    System.out.println(authentication.getPrincipal());
        return ResponseEntity.ok(authService.login((UserDetails) authentication.getPrincipal()));
}
}
