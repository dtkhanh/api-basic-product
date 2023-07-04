package com.example.apibasicproduct.viewmodel.product;

import com.example.apibasicproduct.viewmodel.brand.BrandVm;
import com.example.apibasicproduct.viewmodel.category.CategoryVm;

import java.util.List;

public record ProductDetailVm(
        Long id,
        String name,
        Double price,
        String description,
        String shortDescription,
        Boolean isPublished,
        Boolean isAllowedToOrder,
        int quantity,
        BrandVm brandVm,
        List<CategoryVm> categoryVmList

){
}
