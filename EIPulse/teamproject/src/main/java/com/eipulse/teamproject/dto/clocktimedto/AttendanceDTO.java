package com.eipulse.teamproject.dto.clocktimedto;

import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AttendanceDTO {
    private LocalDate date;
    private Integer empId;
    private String empName;
    private BigDecimal totalHours;
    private Boolean isLate = false;
    private Boolean isEarlyLeave = false;
    private Boolean isHoursNotMet = false;
    private Boolean isOverTime = false;

    public AttendanceDTO() {
    }

    public AttendanceDTO(Attendance attendance) {
        this.date = attendance.getDate();
        this.empId = attendance.getEmployee().getEmpId();
        this.empName = attendance.getEmployee().getEmpName();
        this.totalHours = attendance.getTotalHours();
        this.isLate = attendance.getIsLate();
        this.isEarlyLeave = attendance.getIsEarlyLeave();
        this.isHoursNotMet = attendance.getIsHoursNotMet();
        this.isOverTime = attendance.getIsOverTime();
    }
}
