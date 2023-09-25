package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Employee;
import org.springframework.stereotype.Controller;

import java.util.List;

public interface EmpService {
    Employee saveEmp(Employee employee);
    Employee findEmpById(Integer empId);
    List<Employee> findEmpAll();
    boolean updateEmp(Integer empId,Employee employee);
    boolean deleteEmp(Integer empId);
}
