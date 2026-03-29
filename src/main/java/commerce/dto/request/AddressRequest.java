package commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressRequest(

        @NotBlank(message = "Label is required (e.g., Home, Work)")
        @Size(max = 20)
        @NotNull
        String label,

        @NotBlank(message = "City is required")
        @Size(max = 20)
        @NotNull
        String city,

        @NotBlank(message = "Street is required")
        @Size(max = 100)
        @NotNull
        String street,

        @Size(max = 250)
        String notes
) {
}
