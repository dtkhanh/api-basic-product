package com.example.apibasicproduct.model;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name ="product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private String description;

    private boolean isPublished;

    private boolean isAllowedToOrder;

    private String shortDescription;

    private int quantity;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private List<ProductCategory> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private  List<ProductImage> productImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
