package com.eipulse.teamproject.entity.clocktimeentity;

import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "clock_time")
public class ClockTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clock_time_id")
    private Integer clockTimeId;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "type")
    private String type;


    @JsonBackReference(value = "officeRegions")
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "regions_id")
    private OfficeRegions officeRegions;

    @JsonBackReference(value = "employee-clockTime")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id",referencedColumnName = "emp_id")
    private Employee employee;

    public ClockTime() {

    }

    public ClockTime(LocalDateTime time, String type, OfficeRegions officeRegions, Employee employee) {
        this.time = time;
        this.type = type;
        this.officeRegions = officeRegions;
        this.employee = employee;
    }

    public ClockTime(LocalDateTime time, String type, Employee employee) {
        this.time = time;
        this.type = type;
        this.employee = employee;
    }

}