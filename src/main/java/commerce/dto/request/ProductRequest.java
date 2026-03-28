package commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductRequest(
        @Length(min = 10, max = 150, message = "Product name must be between 10 and 150 character.")
        @NotBlank
        @NotNull
        String name,
        @Length(min = 10, max = 500, message = "Product description must be between 10 and 500 character.")
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
