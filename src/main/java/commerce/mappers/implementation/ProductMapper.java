package commerce.mappers.implementation;

import commerce.dto.request.ProductRequest;
import commerce.dto.response.ProductResponse;
import commerce.entities.Product;
import commerce.mappers.contracts.IProductMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements IProductMapper {
    @Override
    public ProductResponse entityToResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(),
                product.getPrice(), product.getIsAvailable());
    }

    @Override
    public Product requestTooEntity(ProductRequest product) {
        return Product.builder().
                name(product.name())
                .description(product.description())
                .price(product.price())
                .isAvailable(product.isAvailable())
                .build();
    }

    @Override
    public Product responseToEntity(ProductResponse product) {
        return Product.builder().
                name(product.name())
                .description(product.description())
                .price(product.price())
                .isAvailable(product.available())
                .build();
    }



}
