package com.eipulse.teamproject.entity.salaryentity;


import java.time.LocalDateTime;

import com.eipulse.teamproject.dto.salarydto.SalaryHistoryDto;
import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "salary_history")
public class SalaryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "emp_id")
    private Integer empId;

    @JsonBackReference(value = "employee-salary-history")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", insertable = false, updatable = false)
    private Employee employee;

    @Column(name = "original_salary")
    private Integer originalSalary;

    @Column(name = "adjust_salary")
    private Integer adjustSalary;


    @Column(name = "remark", length = 50)
    private String remark;


    @Column(name = "adjusted_date")
    private LocalDateTime adjustedDate;


    public SalaryHistory(SalaryHistoryDto historyDto) {

        this.id = historyDto.getId();
        this.empId = historyDto.getEmpId();
        this.originalSalary = historyDto.getOriginalSalary();
        this.adjustSalary = historyDto.getAdjustSalary();
        this.remark = historyDto.getRemark();
        this.adjustedDate = historyDto.getAdjustedDate();


    }


    public SalaryHistory() {
        super();
    }
}