package com.example.apibasicproduct.viewmodel.category;

import java.util.List;

public record CategoryListVm(
        List<CategoryVm> categoryVms,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
