package com.eipulse.teamproject.entity.salaryentity;


import java.time.LocalDateTime;
import java.util.List;

import com.eipulse.teamproject.dto.salarydto.SalaryMonthRecordDto;
import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salary_month_record", schema = "eipulseproject")
public class SalaryMonthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer recordId;

    @Column(name = "sl_year", nullable = false)
    private Integer slYear;

    @Column(name = "sl_month", nullable = false)
    private Integer slMonth;


    @JsonBackReference(value = "employee-salary-month-records")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @Column(name = "add_sum", nullable = false)
    private Integer addSum;

    @Column(name = "dedu_sum", nullable = false)
    private Integer deduSum;


    @Column(name = "net_salary", nullable = false)
    private Integer netSalary;

    @Column(name = "created_date" ,insertable = false,updatable = false)
    private LocalDateTime createdDate;

    @JsonManagedReference(value = "salary-month-record-detail")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salaryMonthRecord", cascade = CascadeType.ALL)
    private List<SalaryDetail> salaryDetails;



    public SalaryMonthRecord() {
    }

    public SalaryMonthRecord(SalaryMonthRecordDto recordDto) {
        this.slYear = recordDto.getSlYear();
        this.slMonth = recordDto.getSlMonth();

        Employee emp = new Employee();
        emp.setEmpId(recordDto.getEmpId());
        emp.setEmpName(recordDto.getEmpName());
        this.employee = emp;


        this.addSum = recordDto.getAddSum();
        this.deduSum = recordDto.getDeduSum();
        this.netSalary = recordDto.getNetSalary();
        this.recordId = recordDto.getId();
        this.createdDate = recordDto.getCreatedDate();
    }


}