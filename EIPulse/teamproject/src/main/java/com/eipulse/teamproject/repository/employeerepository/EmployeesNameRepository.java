package com.eipulse.teamproject.repository.employeerepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eipulse.teamproject.entity.view.EmployeesName;

public interface EmployeesNameRepository extends JpaRepository<EmployeesName, Integer> {

}