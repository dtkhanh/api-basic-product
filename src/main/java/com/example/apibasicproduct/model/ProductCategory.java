package com.example.apibasicproduct.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table( name = "productCategory")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategory {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    Long id;

    private int displayOder;

    private boolean isFeaturedProduct;

    @ManyToOne
    @JoinColumn( name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn ( name = "category_id", nullable = false)
    private Category category;

}
