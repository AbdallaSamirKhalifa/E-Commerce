package commerce.controllers.assemblers;

import commerce.controllers.ProductController;
import commerce.dto.response.ProductResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements
        RepresentationModelAssembler<ProductResponse, EntityModel<ProductResponse>> {
    @Override
    public EntityModel<ProductResponse> toModel(ProductResponse product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(product.id())).withSelfRel()
                , linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all"),
                linkTo(methodOn(ProductController.class).getAllProductsByPage(
                        "name", "ASC",
                        0, 10)).withRel("allByPage")
        );
    }
}
