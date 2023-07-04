package com.example.apibasicproduct.viewmodel.product;

import java.util.List;

public record ProductListVm(
        List<ProductVm> productVms,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
