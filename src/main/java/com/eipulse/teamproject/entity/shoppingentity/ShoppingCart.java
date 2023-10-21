package com.eipulse.teamproject.entity.shoppingentity;

import com.eipulse.teamproject.entity.Employee;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false,unique = true)
    private Employee employee;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",insertable = false,updatable = false)
    private LocalDateTime updatedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public ShoppingCart() {
    }

    public ShoppingCart(Employee employee) {
        this.employee = employee;
    }
    //    Map<String,<String>>
}