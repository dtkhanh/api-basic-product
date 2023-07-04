package com.example.apibasicproduct.repository;

import com.example.apibasicproduct.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select e from Product e where e.name = ?1 and (?2 is null or e.id != ?2)")
    Product findExistedNameAndId(String name, Long id);

    @Query("select p from Product p where lower(p.name) like %:productName% and (p.brand.name IN :brandName OR (:brandName is null OR :brandName = ''))")
    Page<Product> getProductsWithName(@Param("productName") String productName,String brandName, Pageable pageable);

}
