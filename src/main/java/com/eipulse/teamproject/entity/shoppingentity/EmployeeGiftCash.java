package com.eipulse.teamproject.entity.shoppingentity;

import com.eipulse.teamproject.entity.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "employee_gift_cash")
public class EmployeeGiftCash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_gift_cash_id", nullable = false)
    private Integer id;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gift_cash_list_id", nullable = false)
    private GiftCashList giftCashList;

    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToOne(mappedBy = "employeeGiftCash")
    private Order order;
}