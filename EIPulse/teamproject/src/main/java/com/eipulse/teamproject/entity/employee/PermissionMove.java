package com.eipulse.teamproject.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "permission_move", schema = "new_eipulse")
public class PermissionMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @JsonBackReference(value = "emp-permissionMoves")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee emp;

    @Column(name = "before_permission_name", nullable = false, length = 50)
    private String beforePermissionName;

    @Column(name = "after_permission_name", nullable = false, length = 50)
    private String afterPermissionName;

    @Column(name = "reason")
    private String reason;

    @Column(name = "effect_date")
    private LocalDate effectDate;

    @Column(name = "approver", length = 50)
    private String approver;

    @Column(name = "edit_date",insertable = false,updatable = false)
    private LocalDateTime editDate;

    public PermissionMove() {
    }

    // add 的建構子
    public PermissionMove(Employee emp, String beforePermissionName, String afterPermissionName, String reason, LocalDate effectDate, String approver) {
        this.emp = emp;
        this.beforePermissionName = beforePermissionName;
        this.afterPermissionName = afterPermissionName;
        this.reason = reason;
        this.effectDate = effectDate;
        this.approver = approver;
    }
}