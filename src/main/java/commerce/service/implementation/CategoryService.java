package commerce.service.implementation;

import commerce.dto.request.CategoryRequest;
import commerce.dto.response.CategoryResponse;
import commerce.entities.Category;
import commerce.exceptions.ResourceNotFoundException;
import commerce.repositories.CategoryRepository;
import commerce.service.contract.ICategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService implements ICategoryService {
    private final CategoryRepository repository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = convertToEntity(request);
        category = repository.save(category);
        log.info("\n\tCreating new Category {}", category.getName());
        return convertToResponse(category);
    }

    @Transactional
    @Override
    public CategoryResponse updateCategory(CategoryRequest request, Integer catId) {
        Category category = repository.findById(catId).
                orElseThrow(() -> new ResourceNotFoundException("Category", catId));
        category.setName(request.name());
        log.info("\n\tUpdating Category with id: {}", category.getId());
        return convertToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return repository.findAll().stream().map(this::convertToResponse).toList();
    }

    @Override
    public Page<CategoryResponse> getAllCategoriesByPage(Integer page,
                                                         Integer size,
                                                         String sortBy,
                                                         String sortOrder) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return repository.findAll(pageRequest).map(this::convertToResponse);
    }

    @Override
    public void deleteCategory(Integer catId) {
        log.info("\n\tDeleting Category with id: {}", catId);
        repository.deleteById(catId);
    }

    @Override
    public CategoryResponse getCategoryById(Integer catId) {
        return convertToResponse(repository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category", catId)));
    }

    @Override
    public CategoryResponse convertToResponse(Category category) {

        return new CategoryResponse(category.getId(), category.getName());
    }

    @Override
    public Category convertToEntity(CategoryRequest request) {
        return Category.builder().name(request.name()).build();
    }
}
