package com.example.apibasicproduct.model;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table( name ="category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private boolean isPublished;

    private Long imageId;

    @ManyToOne
    @JoinColumn ( name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<ProductCategory> productCategories = new ArrayList<>();



}
