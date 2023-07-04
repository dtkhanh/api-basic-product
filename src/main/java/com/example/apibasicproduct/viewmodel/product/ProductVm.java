package com.example.apibasicproduct.viewmodel.product;

import com.example.apibasicproduct.model.Product;

public record ProductVm(
        Long id,
        String name,
        Double price,
        Boolean isPublished,
        Boolean isAllowedToOder
) {
    public static ProductVm fromModel(Product product){
        return new ProductVm(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isPublished(),
                product.isAllowedToOrder()
        );
    }
}
