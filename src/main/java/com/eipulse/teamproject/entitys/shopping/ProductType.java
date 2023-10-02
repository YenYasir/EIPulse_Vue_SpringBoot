package com.eipulse.teamproject.entitys.shopping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Integer id;

    @Column(name = "type_name", nullable = false, length = 30)
    private String typeName;

    @Column(name = "subtype_name", nullable = false, length = 30)
    private String subtypeName;

    @OneToMany(mappedBy = "type")
    private Set<Product> products = new LinkedHashSet<>();

}