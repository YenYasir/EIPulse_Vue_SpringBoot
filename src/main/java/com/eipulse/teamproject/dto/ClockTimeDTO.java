package com.eipulse.teamproject.dto;


import com.eipulse.teamproject.entitys.ClockTime;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClockTimeDTO {
    private Integer clockTimeId;
    private LocalDateTime time;
    private String type;
    private String officeRegions;
    private Integer empId;
    private String empName;

    public ClockTimeDTO(ClockTime clockTime) {
        this.clockTimeId = clockTime.getClockTimeId();
        this.time = clockTime.getTime();
        this.type = clockTime.getType();
        if(clockTime.getEmployee() !=null){
            this.empId = clockTime.getEmployee().getEmpId();
            this.empName = clockTime.getEmployee().getEmpName();
        }
        if(clockTime.getOfficeRegions() !=null){
            this.officeRegions = clockTime.getOfficeRegions().getRegionsName();
        }else {
            this.officeRegions = "遠端打卡";
        }
    }

    public ClockTimeDTO() {
    }
}
