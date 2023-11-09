package com.eipulse.teamproject.controller.clocktimecontroller;

import com.eipulse.teamproject.service.clocktimeservice.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
public class AttendanceController {
    private AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    //依區間抓取單員工出勤狀況，最大為單月
    @GetMapping("/attendance/{empId}/{startDate}/{endDate}/{pageNumber}")
    public ResponseEntity<?> findByDateBetweenEmpAttendance(@PathVariable Integer empId, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Integer pageNumber) {
        return new ResponseEntity<>(attendanceService.findByDateBetweenEmpAttendance(startDate, endDate, empId, pageNumber), HttpStatus.OK);
    }

    //依區間抓取全員工出勤狀況，最大為單月
    @GetMapping("/attendance/{startDate}/{endDate}/{pageNumber}")
    public ResponseEntity<?> findByMonthAllEmpAttendance(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Integer pageNumber) {
        return new ResponseEntity<>(attendanceService.findByMonthAllEmpAttendance(startDate, endDate, pageNumber), HttpStatus.OK);
    }

}
