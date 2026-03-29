package commerce.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @Size(min = 10, max = 150, message = "Product name must be between 10 and 150 character.")
        @NotBlank
        @NotNull
        String name,
        @Size(min = 10, max = 500, message = "Product description must be between 10 and 500 character.")
        @NotBlank
        @NotNull
        String description,
        @PositiveOrZero
        BigDecimal price,
        @NotNull
        boolean isAvailable,
        @NotNull
        @Positive
        Integer categoryId
) {
}
