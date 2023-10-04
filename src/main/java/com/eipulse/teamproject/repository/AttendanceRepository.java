package com.eipulse.teamproject.repository;

import com.eipulse.teamproject.entitys.Attendance;
import com.eipulse.teamproject.entitys.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

//    顯示員工選擇日期的當日出勤時數狀況
    Optional<Attendance> findByDateAndEmployee(LocalDate date, Employee employee);

}