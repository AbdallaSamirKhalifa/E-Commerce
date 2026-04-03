package commerce.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRequest(
        @NotNull(message = "Shipping address ID is required")
        Integer addressId,

        @Size(max = 500, message = "Note is too long")
        String orderNote
) {
}
