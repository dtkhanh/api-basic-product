package com.example.apibasicproduct.service;

import com.example.apibasicproduct.exception.DuplicatedException;
import com.example.apibasicproduct.exception.NotFoundException;
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
    public Brand createBrand(BrandPostVm brandPostVm) {
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
                (int) brandPage.getTotalElements(),
                brandPage.getTotalPages(),
                brandPage.isLast()
        );
    }
    public void update(BrandPostVm brandPostVm, Long id){
        validateExistedNameAndId(brandPostVm.name(), id);
        Brand brand = brandRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Brand not found: ", id));
        brand.setPublished(brandPostVm.isPublished());
        brand.setName(brandPostVm.name());
        brandRepository.save(brand);
    }
    private void validateExistedNameAndId(String name, Long id) {
        if(checkExitedNameAndId(name,id)){
            throw new DuplicatedException("Name brand is existed: " , name);
        }
    }
    private boolean checkExitedNameAndId(String name, Long id){
        return brandRepository.findExistedNameAndId(name,id) != null ;
    }
}
