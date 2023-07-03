package com.example.apibasicproduct.controller;

import com.example.apibasicproduct.model.Category;
import com.example.apibasicproduct.repository.CategoryRepository;
import com.example.apibasicproduct.service.CategoryService;
import com.example.apibasicproduct.viewmodel.category.CategoryListVm;
import com.example.apibasicproduct.viewmodel.category.CategoryPostVm;
import com.example.apibasicproduct.viewmodel.category.CategoryVm;
import com.example.apibasicproduct.viewmodel.error.ErrorVm;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;


    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryVm>> getListCategory(){
        List<CategoryVm> categories = categoryRepository.findAll()
                .stream().map(CategoryVm::fromModel ).toList();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/api/categories/paging")
    public ResponseEntity<CategoryListVm> getListCategoryPageable(@RequestParam(value = "pageNo",defaultValue = "0", required = false) int pageNo,
                                                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false)int pageSize){
        return ResponseEntity.ok(categoryService.getCategoryListVm(pageNo,pageSize));
    }

    @GetMapping("/api/categories/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = CategoryVm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorVm.class)))})

    public ResponseEntity<CategoryVm> categoryFindById( @PathVariable("id") Long id){
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }

    @PostMapping("/api/categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CategoryVm.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<CategoryVm> createBrand(@Valid @RequestBody CategoryPostVm categoryPostVm, UriComponentsBuilder uriComponentsBuilder) {
        Category category = categoryService.createCategory(categoryPostVm);
        return ResponseEntity.created(uriComponentsBuilder.replacePath("/api/categories/{id}").buildAndExpand(category.getId()).toUri())
                .body(CategoryVm.fromModel(category));
    }
}
