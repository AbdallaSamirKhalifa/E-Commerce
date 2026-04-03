package commerce.controllers;

import commerce.controllers.assemblers.ProductModelAssembler;
import commerce.dto.request.ProductRequest;
import commerce.dto.response.ProductResponse;
import commerce.service.contract.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final ProductModelAssembler assembler;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{prodId}")
    public ResponseEntity<EntityModel<ProductResponse>> getProductById(@PathVariable Integer prodId) {
        return ResponseEntity.ok(
                assembler
                        .toModel
                                (productService.getProductById(prodId)));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<EntityModel<ProductResponse>> createNewProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        assembler.toModel(
                                productService.createProduct(request)
                        )
                );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<ProductResponse>>> getAllProducts() {

        return ResponseEntity.ok(
                assembler.toCollectionModel(
                        productService.getAllProducts()
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/allByPage")
    public ResponseEntity<CollectionModel<EntityModel<ProductResponse>>> getAllProductsByPage(
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize
    ) {
        return ResponseEntity.ok(
                assembler.toCollectionModel(
                        productService.getAllProductsByPage(
                                page,
                                pageSize,
                                sortBy,
                                sortOrder
                        )
                )
        );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{prodId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Integer prodId) {
        productService.deleteProduct(prodId);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/update/{prodId}")
    public ResponseEntity<EntityModel<ProductResponse>> updateProductById(@PathVariable Integer prodId,
                                                                          @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(
                assembler.toModel(
                        productService.updateProduct(request, prodId)
                )
        );
    }


}
