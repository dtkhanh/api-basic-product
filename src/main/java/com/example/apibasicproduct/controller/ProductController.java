package com.example.apibasicproduct.controller;

import com.example.apibasicproduct.service.ProductService;
import com.example.apibasicproduct.viewmodel.brand.BrandVm;
import com.example.apibasicproduct.viewmodel.error.ErrorVm;
import com.example.apibasicproduct.viewmodel.product.ProductDetailVm;
import com.example.apibasicproduct.viewmodel.product.ProductPostVm;
import com.example.apibasicproduct.viewmodel.product.ProductVm;
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
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductVm>> getListProduct(){
        return ResponseEntity.ok().body(productService.getProducts());
    }
    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductDetailVm> getProductById(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PostMapping("/api/products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = BrandVm.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<ProductDetailVm> createProduct(@Valid @RequestBody ProductPostVm productPostVm, UriComponentsBuilder uriComponentsBuilder){
        ProductDetailVm productDetailVm = productService.createProduct(productPostVm);
        return ResponseEntity.created(uriComponentsBuilder.replacePath("/api/products/{id}").buildAndExpand(productDetailVm.id()).toUri())
                .body(productDetailVm);
    }
}
