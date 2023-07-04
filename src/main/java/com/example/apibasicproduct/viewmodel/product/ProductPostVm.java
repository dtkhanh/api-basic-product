package com.example.apibasicproduct.viewmodel.product;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ProductPostVm(
        @NotBlank String name,
        Double price,
        String description,
        Boolean isAllowedToOrder,
        Boolean isPublished,
        String shortDescription,
        int quantity,
        Long brandId,
        List<Long> categoryIds

) {
}
