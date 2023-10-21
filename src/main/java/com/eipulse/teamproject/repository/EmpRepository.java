package com.eipulse.teamproject.repository;

import com.eipulse.teamproject.entity.Employee;
import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmpRepository extends JpaRepository<Employee,Integer> {



}
