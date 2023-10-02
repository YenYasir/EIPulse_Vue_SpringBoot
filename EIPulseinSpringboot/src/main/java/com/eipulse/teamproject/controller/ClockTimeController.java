package com.eipulse.teamproject.controller;


import com.eipulse.teamproject.entitys.ClockTime;
import com.eipulse.teamproject.service.ClockTimeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ClockTimeController {

    private ClockTimeServiceImp clockTimeServiceImp;

    @Autowired
    public ClockTimeController(ClockTimeServiceImp clockTimeServiceImp) {
        this.clockTimeServiceImp = clockTimeServiceImp;
    }

    @PostMapping("/clockTime/save")
    public ClockTime saveClockTimePage(@RequestParam(name = "empId")Integer empId,
                                          @RequestParam(name = "type")String type){
        LocalDateTime now = LocalDateTime.now();
        return clockTimeServiceImp.saveClockTime(new ClockTime(empId,now,type));
    }
    @GetMapping("/clockTime/byId")
    public List<ClockTime> findAllById(@RequestParam(name = "empId")Integer empId){
        List<ClockTime> clockTimeList = clockTimeServiceImp.findAllByIdTime(empId);
        return clockTimeList;
    }

}
