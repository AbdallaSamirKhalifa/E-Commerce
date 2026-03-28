package commerce.mappers.implementation;

import commerce.dto.request.CategoryRequest;
import commerce.dto.response.CategoryResponse;
import commerce.entities.Category;
import commerce.mappers.contracts.ICategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements ICategoryMapper {
    @Override
    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());

    }

    @Override
    public Category requestToEntity(CategoryRequest category) {
        return Category.builder().name(category.name()).build();
    }

    @Override
    public Category responseToEntity(CategoryResponse category) {
        return Category.builder().name(category.name()).id(category.id()).build();
    }


}
