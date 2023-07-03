package com.example.apibasicproduct.service;

import com.example.apibasicproduct.exception.BadRequestException;
import com.example.apibasicproduct.exception.DuplicatedException;
import com.example.apibasicproduct.exception.NotFoundException;
import com.example.apibasicproduct.model.Category;
import com.example.apibasicproduct.repository.CategoryRepository;
import com.example.apibasicproduct.viewmodel.category.CategoryListVm;
import com.example.apibasicproduct.viewmodel.category.CategoryPostVm;
import com.example.apibasicproduct.viewmodel.category.CategoryVm;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public CategoryListVm getCategoryListVm(int pageNo, int pageSize){
        List<CategoryVm> categoryVms = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<Category> categoryList = categoryPage.getContent();
        for( Category category: categoryList){
            categoryVms.add(CategoryVm.fromModel(category));
        }
        return new CategoryListVm(
                categoryVms,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalPages(),
                categoryPage.getTotalPages(),
                categoryPage.isLast()
        );
    }
    public CategoryVm getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Not found category: ", id));
        return CategoryVm.fromModel(category);
    }
    public Category createCategory(CategoryPostVm categoryPostVm){
        validateExistedNameAndId(categoryPostVm.name(), null);
        Category category = new Category();
        category.setName(categoryPostVm.name());
        category.setDescription(categoryPostVm.description());
        category.setIsPublished(categoryPostVm.isPublished());
        category.setImageId(null);
        if(categoryPostVm.parentId() != null){
            Category categoryParent = categoryRepository.findById(categoryPostVm.parentId())
                    .orElseThrow(()-> new BadRequestException("Parent Category not found"));
            category.setParent(categoryParent);
        }
        return categoryRepository.save(category);
    }

    private void validateExistedNameAndId(String name, Long id) {
        if(checkExitedNameAndId(name,id)){
            throw new DuplicatedException("Name category is existed: " , name);
        }
    }
    private boolean checkExitedNameAndId(String name, Long id){
        return categoryRepository.findExistedNameAndId(name,id) != null;
    }
}
