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

    @Column(name = "before_dept_info", nullable = false)
    private String beforeDeptInfo;

    @Column(name = "after_dept_info", nullable = false)
    private String afterDeptInfo;

    @Column(name = "effect_date")
    private LocalDateTime effectDate;

    @Column(name = "edit_date")
    private LocalDateTime editDate;

    @Column(name = "approver", length = 20)
    private String approver;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee emp;

    @Column(name = "reason", nullable = false, length = 50)
    private String reason;

}