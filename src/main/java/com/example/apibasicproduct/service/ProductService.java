package com.example.apibasicproduct.service;

import com.example.apibasicproduct.exception.BadRequestException;
import com.example.apibasicproduct.exception.DuplicatedException;
import com.example.apibasicproduct.exception.NotFoundException;
import com.example.apibasicproduct.model.Brand;
import com.example.apibasicproduct.model.Category;
import com.example.apibasicproduct.model.Product;
import com.example.apibasicproduct.model.ProductCategory;
import com.example.apibasicproduct.repository.BrandRepository;
import com.example.apibasicproduct.repository.CategoryRepository;
import com.example.apibasicproduct.repository.ProductRepository;
import com.example.apibasicproduct.viewmodel.brand.BrandVm;
import com.example.apibasicproduct.viewmodel.category.CategoryVm;
import com.example.apibasicproduct.viewmodel.product.ProductDetailVm;
import com.example.apibasicproduct.viewmodel.product.ProductListVm;
import com.example.apibasicproduct.viewmodel.product.ProductPostVm;
import com.example.apibasicproduct.viewmodel.product.ProductVm;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductVm> getProducts(){
        return productRepository.findAll()
                .stream().map(ProductVm::fromModel).toList();
    }

    public ProductListVm getProductsPageable(int pageNo, int pageSize){
        List<ProductVm> productVms = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> productList = productPage.getContent();
        for( Product product: productList){
            productVms.add(ProductVm.fromModel(product));
        }
        return new ProductListVm(
                productVms,
                productPage.getNumber(),
                productPage.getSize(),
                (int) productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }



    public ProductDetailVm getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Product not found: ", id));
        Brand brand = product.getBrand();
        List<Category> categories = new ArrayList<>();
        List<CategoryVm> categoryVms =new ArrayList<>();
        if(product.getProductCategories() != null){
            for(ProductCategory productCategory : product.getProductCategories()){
                categories.add(productCategory.getCategory());
            }
            categoryVms = categories.stream()
                    .map(CategoryVm::fromModel).toList();
        }
        return new ProductDetailVm(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getShortDescription(),
                product.isPublished(),
                product.isAllowedToOrder(),
                product.getQuantity(),
                BrandVm.fromModel(brand),
                categoryVms

        );
    }

    public ProductDetailVm createProduct(ProductPostVm productPostVm){
        validateExistedNameAndId(productPostVm.name(),null);
        Product product = new Product();
        product.setName(productPostVm.name());
        product.setPrice(productPostVm.price());
        product.setAllowedToOrder(productPostVm.isAllowedToOrder());
        product.setPublished(productPostVm.isPublished());
        product.setQuantity(productPostVm.quantity());

        Optional<Brand> brand = brandRepository.findById(productPostVm.brandId());
        if(brand.isEmpty()){
            throw new BadRequestException("Not found brand ",  productPostVm.brandId());
        }else {
            product.setBrand(brand.get());
            List<ProductCategory> productCategories = setProductCategory(productPostVm.categoryIds(), product);
            product.setProductCategories(productCategories);
        }
        Product saveProduct = productRepository.save(product);
        return getProductById(saveProduct.getId());
    }

    public List<ProductCategory> setProductCategory(List<Long> categoryIds, Product product){
        List<ProductCategory> productCategories = new ArrayList<>();
        if(!categoryIds.isEmpty()){
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            if(categories.isEmpty()){
                throw new BadRequestException("Not found category ",categories);
            }else if(categories.size() < categoryIds.size()){
                 categoryIds.removeAll(categories.stream().map(Category::getId).toList());
                 throw new BadRequestException("Not found category ",categoryIds);
            }else{
                for(Category category: categories){
                    productCategories.add(ProductCategory.builder()
                                    .product(product)
                                    .category(category).build());
                }
            }
        }
        return productCategories;
    }

    public ProductListVm getListProductWithName(int pageNo, int pageSize, String productName, String brandName){
        List<ProductVm> productVms = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Product> productPage = productRepository.getProductsWithName(productName.trim().toLowerCase(),brandName.trim(),pageable);
        List<Product> productList = productPage.getContent();
        for( Product product: productList){
            productVms.add(ProductVm.fromModel(product));
        }
        return new ProductListVm(
                productVms,
                productPage.getNumber(),
                productPage.getSize(),
                (int) productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    private void validateExistedNameAndId(String name, Long id) {
        if(checkExitedNameAndId(name,id)){
            throw new DuplicatedException("Name product is existed: " , name);
        }
    }
    private boolean checkExitedNameAndId(String name, Long id){
        return productRepository.findExistedNameAndId(name,id) != null ;
    }

}
