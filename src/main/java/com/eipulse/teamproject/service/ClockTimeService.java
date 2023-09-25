package com.eipulse.teamproject.service;

import com.eipulse.teamproject.dto.ClockTimeDTO;
import com.eipulse.teamproject.entitys.ClockTime;

import java.util.List;

public interface ClockTimeService {
    ClockTime saveClockTime(Integer empId,String type,double latitude,double longitude);

    ClockTimeDTO findByEmpIdLastTime(Integer empId);

    List<ClockTimeDTO> findByEmpIdAllTime(Integer empId);
    List<ClockTimeDTO> findAllTime();


}
