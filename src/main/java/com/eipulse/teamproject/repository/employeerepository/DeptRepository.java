package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.entity.employee.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
}