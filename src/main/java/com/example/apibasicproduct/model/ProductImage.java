package com.example.apibasicproduct.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
