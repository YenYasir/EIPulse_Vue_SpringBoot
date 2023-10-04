package com.eipulse.teamproject.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.entitys.Attendance;
import com.eipulse.teamproject.serviceimp.AttendanceServiceImp;

@RestController
public class AttendanceController {
	private AttendanceServiceImp attendanceServiceImp;

	public AttendanceController(AttendanceServiceImp attendanceServiceImp) {
		this.attendanceServiceImp = attendanceServiceImp;
	}

	@PutMapping("/attendance/update")
	public Attendance updateAttendance(@RequestParam("empId") Integer empId,
			@RequestParam(name = "startTime", defaultValue = "2023-09-24T00:00:00") LocalDateTime startTime,
			@RequestParam(name = "endTime", defaultValue = "2023-09-24T23:59:00") LocalDateTime endTime) {

		return attendanceServiceImp.updateAttendance(empId, startTime, endTime);
	}
}