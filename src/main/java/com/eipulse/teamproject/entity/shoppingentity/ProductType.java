package com.eipulse.teamproject.entity.shoppingentity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product_type")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Integer id;

    @Column(name = "type_name", nullable = false, length = 30)
    private String typeName;

    @OneToMany(mappedBy = "productType")
    @JsonManagedReference
    private Set<SubType> subTypes = new LinkedHashSet<>();



    public ProductType(String typeName) {
        this.typeName = typeName;
    }

    public ProductType() {
    }
}