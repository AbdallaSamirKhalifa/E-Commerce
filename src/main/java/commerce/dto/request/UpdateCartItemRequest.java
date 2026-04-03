package commerce.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record UpdateCartItemRequest (
        @NotNull(message = "Product ID cannot be null")
        Integer productId,
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        Integer quantity


) {
}
