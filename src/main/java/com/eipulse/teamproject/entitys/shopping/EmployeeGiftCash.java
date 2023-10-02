package com.eipulse.teamproject.entitys.shopping;

import com.eipulse.teamproject.entitys.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employee_gift_cash")
public class EmployeeGiftCash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_gift_cash_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee emp;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToMany(mappedBy = "employeeGiftCash")
    private Set<Order> orders = new LinkedHashSet<>();

}