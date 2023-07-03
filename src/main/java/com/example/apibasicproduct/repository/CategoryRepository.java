package com.example.apibasicproduct.repository;

import com.example.apibasicproduct.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select e from Category e where e.name = ?1 and (?2 is null or e.id != ?2)")
    Category findExistedNameAndId(String name, Long id);
}
