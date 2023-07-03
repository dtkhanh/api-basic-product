package com.example.apibasicproduct.viewmodel.brand;

import com.example.apibasicproduct.model.Brand;
import jakarta.validation.constraints.NotBlank;

public record BrandPostVm(@NotBlank String name, Boolean isPublished) {
    public Brand toModel(){
        Brand brand = new Brand();
        brand.setName(name);
        brand.setPublished(isPublished);
        return brand;
    }
}
