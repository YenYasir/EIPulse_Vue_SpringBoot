package com.eipulse.teamproject.controller.clocktimecontroller;


import com.eipulse.teamproject.dto.clocktimedto.ClockTimeDTO;
import com.eipulse.teamproject.entity.clocktimeentity.ClockTime;
import com.eipulse.teamproject.service.clocktimeservice.ClockTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class ClockTimeController {

    private ClockTimeService clockTimeService;

    @Autowired
    public ClockTimeController(ClockTimeService clockTimeService) {
        this.clockTimeService = clockTimeService;
    }

    //打卡資料寫入
    @PostMapping("/clockTime")
    public ResponseEntity<?> postTime(@RequestBody ClockTimeDTO clockTimeDTO){
        System.out.println(clockTimeDTO);
        try {
            ClockTime result = clockTimeService.saveClockTime(clockTimeDTO);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    //抓取單員工當日是否有打卡記錄，如有則回傳最後一筆，沒有則不顯示，用於主頁顯示
    @GetMapping("/clockTime/last/{empId}")
    public ResponseEntity<?> findEmpLatsTime(@PathVariable Integer empId){
        try {
            return new ResponseEntity<>(clockTimeService.findByEmpIdToDayLastTime(empId),HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //選擇要查詢日期的單員工的打卡記錄
    @GetMapping("/clockTimes/{empId}/{startDate}/{endDate}/{pageNumber}")
    public ResponseEntity<?>findByEmpClockTimeByDay(@PathVariable Integer empId,@PathVariable LocalDate startDate,@PathVariable LocalDate endDate,@PathVariable Integer pageNumber){
        try {
            return new ResponseEntity<>(clockTimeService.findClockTimeByEmpByDate(empId,startDate,endDate,pageNumber),HttpStatus.OK);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //選擇要查詢日期的所有員工的打卡記錄
    @GetMapping("/clockTimes/{startDate}/{endDate}/{pageNumber}")
    public ResponseEntity<?>findClockTimeByDay(@PathVariable LocalDate startDate,@PathVariable LocalDate endDate,@PathVariable Integer pageNumber){
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(pageNumber);
        try {
            return new ResponseEntity<>(clockTimeService.findClockTimeByDate(startDate,endDate,pageNumber),HttpStatus.OK);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
