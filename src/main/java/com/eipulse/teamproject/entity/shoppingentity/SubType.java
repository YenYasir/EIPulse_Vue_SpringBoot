package com.eipulse.teamproject.entity.shoppingentity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sub_type")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_type_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    @JsonBackReference
    private ProductType productType;

    @Column(name = "sub_name", nullable = false, length = 20)
    private String subName;

    @JsonManagedReference
    @OneToMany(mappedBy = "subType")
    private Set<Product> products = new LinkedHashSet<>();

    public SubType(ProductType productType, String subName) {
        this.productType = productType;
        this.subName = subName;
    }

    public SubType() {
    }

}