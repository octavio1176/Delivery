package com.example.security.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)

    @PositiveOrZero
    private Integer quantity;

    @Version
    @Transient
    private Long version;

    @Column(nullable = false , unique = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
