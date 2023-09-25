package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "ClockTime")
public class ClockTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClockTimeId")
    private Integer clockTimeId;
    @Column(name = "EmpId")
    private Integer empId;
    @Column(name = "ClockTime")
    private LocalDateTime clockTime;
    @Column(name = "ClockTimeType")
    private String clockTimeType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmpId",referencedColumnName = "EmpId",insertable = false, updatable = false)
    private Employee employee;

    public ClockTime() {
    }

    public ClockTime(Integer clockTimeId, Integer empId, LocalDateTime clockTime, String clockTimeType, Employee employee) {
        this.clockTimeId = clockTimeId;
        this.empId = empId;
        this.clockTime = clockTime;
        this.clockTimeType = clockTimeType;
        this.employee = employee;
    }

    public ClockTime(Integer empId, LocalDateTime clockTime, String clockTimeType) {
        this.empId = empId;
        this.clockTime = clockTime;
        this.clockTimeType = clockTimeType;
    }


}