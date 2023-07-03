package com.example.apibasicproduct.viewmodel.category;

import com.example.apibasicproduct.model.Category;

public record CategoryVm(Long id, String name, String description,Boolean isPublished, Long imaged, Long parentId) {
    public static CategoryVm fromModel(Category category){
        return new CategoryVm(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIsPublished(),
                category.getImageId(),
                category.getParent() == null ? 0L : category.getParent().getId()
                );
    }
}
