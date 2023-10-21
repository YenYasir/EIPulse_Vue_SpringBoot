package com.eipulse.teamproject.entity.clocktimeentity;

import com.eipulse.teamproject.entity.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


//MySQL的boolean會轉為tinyint，0為false，1為true
@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @Column(name = "total_hours", nullable = false, precision = 4, scale = 2)
    private BigDecimal totalHours;

    @Column(name = "is_late", nullable = false)
    private Boolean isLate = false;

    @Column(name = "is_early_leave", nullable = false)
    private Boolean isEarlyLeave = false;

    @Column(name = "is_hours_not_met", nullable = false)
    private Boolean isHoursNotMet = false;

    @Column(name = "is_over_time", nullable = false)
    private Boolean isOverTime = false;


}