package commerce.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record AddressRequest(

        @NotBlank(message = "Label is required (e.g., Home, Work)")
        @Max(value = 20)
        @NotNull
        String label,

        @NotBlank(message = "City is required")
        @Max(value = 20)
        @NotNull
        String city,

        @NotBlank(message = "Street is required")
        @Length(max = 100)
        @NotNull
        String street,

        @Length(max = 250, min = 50)
        String notes
) {
}
