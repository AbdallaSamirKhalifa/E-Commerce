package commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * Error response DTO for responding with clear status code and messages and metadata for customer
 * */
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd - HH:mm:ss")
    private LocalDateTime timestamp;
    private Integer status;
    /**
     * {@summary Short description of the error type}
     *
     */
    private String error;
    /**
     * {@summary Detailed error message}
     *
     */
    private String message;
    /**
     * {@summary Request URI path}
     *
     */
    private String path;
}
