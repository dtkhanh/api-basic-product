package com.example.apibasicproduct.repository;

import com.example.apibasicproduct.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
