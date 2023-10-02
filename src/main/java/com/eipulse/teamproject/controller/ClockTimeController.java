package com.eipulse.teamproject.controller;


import com.eipulse.teamproject.dto.ClockTimeDTO;
import com.eipulse.teamproject.entitys.ClockTime;
import com.eipulse.teamproject.service.ClockTimeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClockTimeController {

    private ClockTimeServiceImp clockTimeServiceImp;

    @Autowired
    public ClockTimeController(ClockTimeServiceImp clockTimeServiceImp) {
        this.clockTimeServiceImp = clockTimeServiceImp;
    }

    //OK
    @PostMapping("/clockTime/postTime")
    public String postTime(@RequestParam("empId")Integer empId, @RequestParam("type")String type,
                           @RequestParam("latitude") double latitude,@RequestParam("longitude") double longitude){
        ClockTime clockTime = clockTimeServiceImp.saveClockTime(empId,type,latitude,longitude);
        return "redirect:/";
    }
    //OK
    @GetMapping("/clockTime/{empId}")
    public ClockTimeDTO findByEmpIdTime(@PathVariable Integer empId){
        return clockTimeServiceImp.findByEmpIdLastTime(empId);
    }

//    OK
    @ResponseBody///將單獨controller設計為json格式不需要設整個calss為restcontroller
    @GetMapping("/clockTime/{empId}/all")
    public  List<ClockTimeDTO> findByEmpIdAllTime(@PathVariable Integer empId){
        return clockTimeServiceImp.findByEmpIdAllTime(empId);
    }
//OK
    @GetMapping("/clockTime/all")
    public  List<ClockTimeDTO> findAllTime(){
        return clockTimeServiceImp.findAllTime();
    }
}
