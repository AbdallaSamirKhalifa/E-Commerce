package commerce.controllers.assemblers;

import commerce.controllers.CategoryController;
import commerce.dto.response.CategoryResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements
        RepresentationModelAssembler<CategoryResponse, EntityModel<CategoryResponse>> {
    @Override
    public EntityModel<CategoryResponse> toModel(CategoryResponse cart) {
        return EntityModel.of(cart,
                linkTo(methodOn(CategoryController.class).getCategoryById(cart.id())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("all"),
                linkTo(methodOn(CategoryController.class).
                        getAllCategoriesByPage("name", "ASC",
                                0, 10)).withRel("allByPage")
        );
    }

    @Override
    public CollectionModel<EntityModel<CategoryResponse>>
    toCollectionModel(Iterable<? extends CategoryResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
