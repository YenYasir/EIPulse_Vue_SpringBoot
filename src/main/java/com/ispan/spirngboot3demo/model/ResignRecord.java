package com.ispan.spirngboot3demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "resign_record")
@Data
public class ResignRecord {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "reason")
    private  String reason;

    @Column(name = "leave_date")
    private LocalDate leaveDate;

    @Column(name = "approver")
    private String approver;

    @Column(name="edit_date")
    private LocalDate editDate;


    @OneToOne(fetch = FetchType.LAZY)
//      @JoinColumn(name = "emp_id", referencedColumnName = "empId", insertable = false, updatable = false)
    @JoinColumn(name = "empId", referencedColumnName = "emp_id")//, insertable = false, updatable = false)
    private Employee employee;

}
