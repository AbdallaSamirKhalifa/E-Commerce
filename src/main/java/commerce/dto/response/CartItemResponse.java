package commerce.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(
        Integer cartItemId,
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal
) {
}
