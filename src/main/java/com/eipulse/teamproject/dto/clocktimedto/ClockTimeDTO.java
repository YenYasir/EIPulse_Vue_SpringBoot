package com.eipulse.teamproject.dto.clocktimedto;


import com.eipulse.teamproject.entity.clocktimeentity.ClockTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClockTimeDTO {
    private Integer clockTimeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private Integer empId;
    private String type;
    private String officeRegions;
    private String empName;

    //數值為 0 時不序列化
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double latitude = 0.0;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double longitude = 0.0;


    public ClockTimeDTO() {
    }

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

    public ClockTimeDTO( Integer empId, String type, String officeRegions) {
        this.empId = empId;
        this.type = type;
        this.officeRegions = officeRegions;
    }
}
