package com.example.apibasicproduct.viewmodel.category;

import com.example.apibasicproduct.model.Category;

public record CategoryDetailVm(Long id, String name, String description,Boolean isPublished, Long imaged, Long parentId) {
    public static CategoryDetailVm fromModel(Category category){
        return new CategoryDetailVm(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIsPublished(),
                null,
                category.getParent() == null ? -1 : category.getParent().getId()
        );
    }
}
