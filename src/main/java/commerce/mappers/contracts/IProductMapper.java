package commerce.mappers.contracts;

import commerce.dto.request.ProductRequest;
import commerce.dto.response.ProductResponse;
import commerce.entities.Product;

public interface IProductMapper {
    ProductResponse entityToResponse(Product product);
    Product requestTooEntity(ProductRequest product);
    Product responseToEntity(ProductResponse product);
}
