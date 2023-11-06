package com.eipulse.teamproject.entity.formapproval;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
@Entity
@Table(name = "WorkSchedule")
public class WorkSchedule {

    @Id
    private int scheduleID;
    @Column(name="Emp_id")
    private int empId;
    private int weekday;
    @Column(name="start_time")
    private LocalTime startTime;
    @Column(name="end_time")
    private LocalTime endTime;
    @Column(name="lunch_start")
    private LocalTime lunchStart;
    @Column(name="lunch_end")
    private LocalTime lunchEnd;
}
