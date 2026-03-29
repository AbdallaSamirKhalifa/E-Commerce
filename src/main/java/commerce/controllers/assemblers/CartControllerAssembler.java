package commerce.controllers.assemblers;

import commerce.controllers.CartController;
import commerce.dto.response.CartResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartControllerAssembler implements
        RepresentationModelAssembler<CartResponse, EntityModel<CartResponse>> {
    @Override
    public EntityModel<CartResponse> toModel(CartResponse cart) {
        return EntityModel.of(cart,
                linkTo(methodOn(CartController.class).getMyCart()).withSelfRel()
        );
    }
}
