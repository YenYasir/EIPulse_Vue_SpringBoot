package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.spirngboot3demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

	
}
