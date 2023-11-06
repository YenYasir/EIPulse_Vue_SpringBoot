package com.eipulse.teamproject.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "emergency", schema = "new_eipulse")
public class Emergency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emergency_id", nullable = false)
    private Integer emergencyId;

    @Column(name = "emergency_name", nullable = false, length = 50)
    private String emergencyName;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "relation", nullable = false, length = 20)
    private String relation;


    @JsonBackReference(value = "emp-emergencies")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee emp;

    public Emergency() {
    }

    public Emergency(String emergencyName, String phone, String relation, Employee emp) {
        this.emergencyName = emergencyName;
        this.phone = phone;
        this.relation = relation;
        this.emp = emp;  // 這裡我加了這行，設定員工
    }
}