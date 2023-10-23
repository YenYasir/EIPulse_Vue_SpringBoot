package com.eipulse.teamproject.entity.clocktimeentity;

import com.eipulse.teamproject.entity.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "regions_id")
    private OfficeRegions officeRegions;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id",referencedColumnName = "emp_id")
    private Employee employee;

//    public String getEmployee() {
////                    三元運算子 if !=null return  else  return null
//        return (this.employee !=null) ? this.employee.getEmpId() + this.employee.getEmpName():null;
//    }

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