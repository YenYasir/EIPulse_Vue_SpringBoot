package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Attendance;
import com.eipulse.teamproject.entitys.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceService {
    Attendance updateAttendance(Integer empId, LocalDateTime startTime, LocalDateTime endTime);

    Attendance findByEmpIdLastAttendance(Integer empId);
    List<Attendance> findByEmpIdAllAttendance(Integer empId);

    List<Attendance> findAllEmpAttendance();
}
