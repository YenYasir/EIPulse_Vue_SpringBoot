package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.model.EmployeeInfo;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Integer> {

}
