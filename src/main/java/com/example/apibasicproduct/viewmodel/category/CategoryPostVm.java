package com.example.apibasicproduct.viewmodel.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryPostVm(@NotBlank String name, String description, Boolean isPublished,Long parentId) {
}
