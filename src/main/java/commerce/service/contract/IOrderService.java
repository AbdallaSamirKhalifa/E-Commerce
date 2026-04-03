package commerce.service.contract;

import commerce.dto.request.OrderRequest;
import commerce.dto.response.OrderResponse;

public interface IOrderService {
    OrderResponse checkout(OrderRequest request);
}
