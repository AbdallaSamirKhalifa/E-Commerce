package commerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record RegistrationRequest(

        @Email(message = "Please enter a valid email address")
        @NotNull
        @NotBlank
        String email,

        @Size(min = 10, max = 50,message ="Username must be between 10 and 50 characters length" )
        @NotNull
        @NotBlank
        String username,

        @Size(max = 255, min = 8, message = "Your password must be between 8 and 255 characters length")
        @NotNull
        @NotBlank
        String password,

        @Size(min = 5, max = 100, message = "First name must be between 5 and 100 characters length")
        @NotNull
        @NotBlank
        String firstName,

        @Length(min = 5, max = 100, message = "Last name must be between 5 and 100 characters length")
        @NotNull
        @NotBlank
        String lastName
) {
}
