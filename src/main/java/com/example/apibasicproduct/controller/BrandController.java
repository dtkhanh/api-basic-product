package com.example.apibasicproduct.controller;

import com.example.apibasicproduct.model.Brand;
import com.example.apibasicproduct.repository.BrandRepository;
import com.example.apibasicproduct.service.BrandService;
import com.example.apibasicproduct.viewmodel.brand.BrandListVm;
import com.example.apibasicproduct.viewmodel.brand.BrandPostVm;
import com.example.apibasicproduct.viewmodel.brand.BrandVm;
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
public class BrandController {
    private final BrandService brandService;

    private final BrandRepository brandRepository;

    public BrandController(BrandService brandService, BrandRepository brandRepository) {
        this.brandService = brandService;
        this.brandRepository = brandRepository;
    }

    @GetMapping("/api/brands")
    public ResponseEntity<List<BrandVm>> listBrand(){
        List<BrandVm> brandVms = brandRepository.findAll()
                .stream()
                .map(BrandVm::fromModel)
                .toList();
        return ResponseEntity.ok(brandVms);
    }

    @GetMapping("/api/brands/paging")
    public ResponseEntity<BrandListVm> listPageableBrands(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                          @RequestParam(value ="pageSize", defaultValue = "10", required = false) int pageSize){
        return ResponseEntity.ok(brandService.getBrands(pageNo,pageSize));
    }
    @PostMapping("/api/brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = BrandVm.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = Error.class)))})
    public ResponseEntity<BrandVm> createBrand(@Valid @RequestBody BrandPostVm brandPostVm, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        Brand brand = brandService.createBrand(brandPostVm);
        return ResponseEntity.created(uriComponentsBuilder.replacePath("/api/brands/{id}").buildAndExpand(brand.getId()).toUri())
                .body(BrandVm.fromModel(brand));
    }

}
