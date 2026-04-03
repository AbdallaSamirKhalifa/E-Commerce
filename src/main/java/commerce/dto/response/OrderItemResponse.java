package commerce.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal priceAtPurchase,
        BigDecimal subtotal
) {
}
