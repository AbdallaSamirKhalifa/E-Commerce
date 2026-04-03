package commerce.security.config;

import org.hibernate.SessionCheckMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.SessionManagementDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())//for testing
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                                        .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(
                        session->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
