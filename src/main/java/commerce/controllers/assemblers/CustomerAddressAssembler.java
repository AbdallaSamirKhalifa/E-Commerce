package commerce.controllers.assemblers;

import ch.qos.logback.core.util.JNDIUtil;
import commerce.controllers.CustomerAddressController;
import commerce.dto.response.AddressResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerAddressAssembler implements
    RepresentationModelAssembler<AddressResponse, EntityModel<AddressResponse>> {

//        @Override
//        public EntityModel<CategoryResponse> toModel(CategoryResponse cart) {
//            return EntityModel.of(cart,
//                    linkTo(methodOn(CategoryController.class).getCategoryById(cart.id())).withSelfRel(),
//                    linkTo(methodOn(CategoryController.class).getAllCategories()).withRel("all"),
//                    linkTo(methodOn(CategoryController.class).
//                            getAllCategoriesByPage("name", "ASC",
//                                    0, 10)).withRel("allByPage")
//            );
//        }

    @Override
    public EntityModel<AddressResponse> toModel(AddressResponse address) {
        return EntityModel.of(address,
                linkTo(methodOn(CustomerAddressController.class).getAddressById(address.id())).withSelfRel(),
                linkTo(methodOn(CustomerAddressController.class).getAllAddresses()).withRel("all"));
    }
}
