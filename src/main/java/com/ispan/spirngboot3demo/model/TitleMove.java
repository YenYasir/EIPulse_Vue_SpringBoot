package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "title_move", schema = "new_eipulse")
public class TitleMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee emp;

    @Column(name = "before_dept_info", nullable = false)
    private String beforeDeptInfo;

    @Column(name = "after_dept_info", nullable = false)
    private String afterDeptInfo;

    @Column(name = "reason", nullable = false, length = 50)
    private String reason;

    @Column(name = "effect_date")
    private LocalDate effectDate;

    @Column(name = "approver", length = 20)
    private String approver;

    @Column(name = "edit_date",insertable = false,updatable = false)
    private LocalDateTime editDate;

    public TitleMove() {
    }

    public TitleMove(Employee emp, String beforeDeptInfo, String afterDeptInfo, String reason, LocalDate effectDate, String approver) {
        this.emp = emp;
        this.beforeDeptInfo = beforeDeptInfo;
        this.afterDeptInfo = afterDeptInfo;
        this.reason = reason;
        this.effectDate = effectDate;
        this.approver = approver;
    }
}