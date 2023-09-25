package com.eipulse.teamproject.repository;

import com.eipulse.teamproject.entitys.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<Employee,Integer> {

}
