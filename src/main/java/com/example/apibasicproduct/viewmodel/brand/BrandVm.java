package com.example.apibasicproduct.viewmodel.brand;

import com.example.apibasicproduct.model.Brand;

public record BrandVm(Long id, String name , Boolean isPublished) {
    public static BrandVm fromModel(Brand brand){
        return new BrandVm(brand.getId(), brand.getName(), brand.isPublished());
    }
}
