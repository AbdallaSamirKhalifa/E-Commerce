package commerce.controllers;

import commerce.controllers.assemblers.CategoryModelAssembler;
import commerce.dto.request.CategoryRequest;
import commerce.dto.response.CategoryResponse;
import commerce.service.contract.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    private final CategoryModelAssembler assembler;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategoryResponse>> getCategoryById(@PathVariable("id") Integer catId) {
        return ResponseEntity.ok(assembler.toModel(categoryService.getCategoryById(catId)));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<EntityModel<CategoryResponse>> createNewCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(assembler.
                        toModel(categoryService.createCategory(categoryRequest)));
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.
                ok(assembler.
                        toCollectionModel(categoryService.
                                getAllCategories()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/allByPage")
    public ResponseEntity<CollectionModel<EntityModel<CategoryResponse>>> getAllCategoriesByPage(
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize

    ) {
        return ResponseEntity.
                ok(assembler.
                        toCollectionModel(categoryService.
                                getAllCategoriesByPage(page, pageSize, sortBy, sortOrder)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{catId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("catId") Integer catId) {
        categoryService.deleteCategory(catId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<EntityModel<CategoryResponse>> updateCategoryById(@PathVariable("catId") Integer catId,
                                                                            @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(assembler.
                toModel(categoryService.updateCategory(categoryRequest, catId)));
    }

}
