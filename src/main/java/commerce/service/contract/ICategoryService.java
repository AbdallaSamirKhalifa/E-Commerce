package commerce.service.contract;

import commerce.dto.request.CategoryRequest;
import commerce.dto.response.CategoryResponse;
import commerce.entities.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(CategoryRequest request, Integer catId);

    List<CategoryResponse> getAllCategories();

    Page<CategoryResponse> getAllCategoriesByPage(Integer page, Integer size, String sortBy, String sortOrder);

    void deleteCategory(Integer catId);

    CategoryResponse getCategoryById(Integer catId);


}
