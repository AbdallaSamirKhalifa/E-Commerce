package commerce.mappers.contracts;

import commerce.dto.request.CategoryRequest;
import commerce.dto.response.CategoryResponse;
import commerce.entities.Category;

public interface ICategoryMapper {
    CategoryResponse entityToResponse(Category category);
    Category requestToEntity(CategoryRequest category);
    Category responseToEntity(CategoryResponse category);
}
