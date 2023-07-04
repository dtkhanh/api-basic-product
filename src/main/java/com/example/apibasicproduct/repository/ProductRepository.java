package com.example.apibasicproduct.repository;

import com.example.apibasicproduct.model.Brand;
import com.example.apibasicproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select e from Product e where e.name = ?1 and (?2 is null or e.id != ?2)")
    Brand findExistedNameAndId(String name, Long id);
}
