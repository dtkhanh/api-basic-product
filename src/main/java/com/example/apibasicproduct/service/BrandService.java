package com.example.apibasicproduct.service;

import com.example.apibasicproduct.model.Brand;
import com.example.apibasicproduct.repository.BrandRepository;
import com.example.apibasicproduct.viewmodel.brand.BrandListVm;
import com.example.apibasicproduct.viewmodel.brand.BrandPostVm;
import com.example.apibasicproduct.viewmodel.brand.BrandVm;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    public Brand createBrand(BrandPostVm brandPostVm) throws Exception {
        validateExistedNameAndId(brandPostVm.name(), null);
        return brandRepository.save(brandPostVm.toModel());
    }

    public BrandListVm getBrands(int pageNo, int pageSize){
        List<BrandVm> brandVmList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        List<Brand> brandList = brandPage.getContent();
        for( Brand brand: brandList){
            brandVmList.add(BrandVm.fromModel(brand));
        }
        return new BrandListVm(
                brandVmList,
                brandPage.getNumber(),
                brandPage.getSize(),
                brandPage.getTotalPages(),
                brandPage.getTotalPages(),
                brandPage.isLast()
        );
    }
    private void validateExistedNameAndId(String name, Long id) throws Exception {
        if(checkExitedNameAndId(name,id)){
            throw new Exception("Name brand is existed:" + name);
        }
    }
    private boolean checkExitedNameAndId(String name, Long id){
        return brandRepository.findExistedNameAndId(name,id) != null ;
    }
}
