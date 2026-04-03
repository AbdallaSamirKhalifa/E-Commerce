package commerce.controllers;

import commerce.controllers.assemblers.CartControllerAssembler;
import commerce.dto.request.AddToCartRequest;
import commerce.dto.request.UpdateCartItemRequest;
import commerce.dto.response.CartResponse;
import commerce.service.contract.ICartService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/cart")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;
    private final CartControllerAssembler assembler;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<EntityModel<CartResponse>> getMyCart() {
        return ResponseEntity.ok(
                assembler.toModel(cartService.getMyCart())
        );
    }

    @PostMapping("/addToCart")
    public ResponseEntity<EntityModel<CartResponse>> addToCart(
            @RequestBody @Valid AddToCartRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                assembler.toModel(cartService.addItemToCart(request))
        );
    }

    @PatchMapping("/increase")
    public ResponseEntity<EntityModel<CartResponse>> increaseCartItem(@RequestBody @Valid UpdateCartItemRequest request){

        return ResponseEntity.ok(
                assembler.toModel(cartService.increaseCartItem(request))
        );
    }

    @PatchMapping("/decrease")
    public ResponseEntity<EntityModel<CartResponse>> decreaseCartItem(@RequestBody @Valid UpdateCartItemRequest request){

        return ResponseEntity.ok(
                assembler.toModel(cartService.decreaseCartItem(request))
        );
    }

    @PatchMapping("/{prodId}")
    public ResponseEntity<EntityModel<CartResponse>> removeItemFromCart(@PathVariable Integer prodId){
        return ResponseEntity.ok(assembler.toModel(
                cartService.removeItemFromCart(prodId)
        ));
    }
}
