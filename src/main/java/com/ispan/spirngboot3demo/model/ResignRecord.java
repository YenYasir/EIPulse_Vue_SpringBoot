package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "resign_record", schema = "new_eipulse")
public class ResignRecord {

    @Id
    @Column(name = "resign_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false,unique = true)
    private Employee emp;

    @Column(name = "approver", length = 50)
    private String approver;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "edit_date",insertable = false,updatable = false)
    private LocalDate editDate;

    public ResignRecord() {
    }

    public ResignRecord(Employee emp, String reason) {
        this.emp = emp;
        this.reason = reason;
    }

    public ResignRecord(Integer id, Employee empId, String approver, String reason) {
        this.id = id;
        this.emp = emp;
        this.approver = approver;
        this.reason = reason;
    }

    public ResignRecord(Employee emp, String approver, String reason) {
        this.emp = emp;
        this.approver = approver;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ResignRecord{" +
                "id=" + id +
                ", emp=" + emp +
                ", approver='" + approver + '\'' +
                ", reason='" + reason + '\'' +
                ", editDate=" + editDate +
                '}';
    }
}