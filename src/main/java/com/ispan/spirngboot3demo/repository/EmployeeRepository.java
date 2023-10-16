package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}