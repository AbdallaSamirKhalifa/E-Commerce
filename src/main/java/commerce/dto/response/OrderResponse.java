package commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Integer orderId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd - HH:mm:ss")
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String note,
        List<OrderItemResponse> items
) {
}
