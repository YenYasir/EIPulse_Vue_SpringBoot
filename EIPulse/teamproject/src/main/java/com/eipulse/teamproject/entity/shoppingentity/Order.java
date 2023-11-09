package com.eipulse.teamproject.entity.shoppingentity;

import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;


    
    @JsonBackReference(value = "employee-orders")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;


    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "status", length = 10)
    private String status;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",insertable = false,updatable = false)
    private LocalDateTime updatedAt;

    @JsonManagedReference(value = "order")
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new LinkedHashSet<>();


    public Order() {
    }

    public Order(Employee employee) {
        this.employee = employee;
    }


}