package commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CategoryRequest(
        @NotNull(message = "Category name is required")
        @NotBlank(message = "Category name is required")
        @Length(min = 5, max = 100, message = "Category name must be between 5 and 100 characters")
        String name
) {
}
