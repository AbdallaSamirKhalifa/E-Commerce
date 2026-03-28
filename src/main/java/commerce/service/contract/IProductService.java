package commerce.service.contract;

import commerce.dto.request.ProductRequest;
import commerce.dto.response.ProductResponse;
import commerce.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(ProductRequest request, Integer prodId);

    List<ProductResponse> getAllProducts();

    Page<ProductResponse> getAllProductsByPage(Integer page, Integer size, String sortBy, String sortOrder);

    void deleteProduct(Integer prodId);

    ProductResponse getProductById(Integer prodId);


}
