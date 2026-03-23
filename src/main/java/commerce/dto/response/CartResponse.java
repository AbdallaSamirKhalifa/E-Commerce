package commerce.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        Integer cartId,
        Integer customerId,
        List<CartItemResponse> items,
        BigDecimal totalCartPrice
) {
}
