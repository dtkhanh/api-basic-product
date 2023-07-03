package com.example.apibasicproduct.viewmodel.brand;

import java.util.List;

public record BrandListVm(
        List<BrandVm> brandVms,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
