package commerce.service.implementation;

import commerce.dto.request.ProductRequest;
import commerce.dto.response.ProductResponse;
import commerce.entities.Category;
import commerce.entities.Product;
import commerce.exceptions.ResourceNotFoundException;
import commerce.mappers.contracts.ICategoryMapper;
import commerce.mappers.contracts.IProductMapper;
import commerce.repositories.ProductRepository;
import commerce.service.contract.ICategoryService;
import commerce.service.contract.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ICategoryService categoryService;
    private final IProductMapper productMapper;
    private final ICategoryMapper categoryMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryMapper.responseToEntity(categoryService.getCategoryById(request.categoryId()));
        Product product = productMapper.requestTooEntity(request);
        product.setCategory(category);
        Product newProduct = productRepository.save(product);
        log.info("\n\tCreating new product with name: {}, id: {}", newProduct.getName(), newProduct.getId());

        return productMapper.toResponse(newProduct);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(ProductRequest request, Integer prodId) {
        Category category = categoryMapper.responseToEntity(categoryService.getCategoryById(request.categoryId()));
        Product product = productRepository.findById(prodId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", prodId));
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setIsAvailable(request.isAvailable());
        product.setCategory(category);
        log.info("\n\tUpdating product with id: {}", product.getId());

        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().
                stream().
                map(productMapper::toResponse).
                toList();
    }

    @Override
    public Page<ProductResponse> getAllProductsByPage(Integer page,
                                                      Integer size,
                                                      String sortBy,
                                                      String sortOrder) {
        PageRequest pageRequest = PageRequest.of(page,
                size,
                Sort.by
                        (Sort
                                        .Direction
                                        .fromString(sortOrder),
                                sortBy));

        return productRepository.findAll(pageRequest).
                map(productMapper::toResponse);
    }

    @Override
    public void deleteProduct(Integer prodId) {
        log.info("\n\tDeleting product with id: {}", prodId);
        productRepository.deleteById(prodId);
    }

    @Override
    public ProductResponse getProductById(Integer prodId) {
        return productRepository.findById(prodId).
                map(productMapper::toResponse).
                orElseThrow(() -> new ResourceNotFoundException("Product", prodId));
    }


}
