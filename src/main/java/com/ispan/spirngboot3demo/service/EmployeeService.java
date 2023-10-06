package com.ispan.spirngboot3demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;
	
    
	
    private String generateSequence() {
        // 根据您的需求实现顺序号生成逻辑，可以是简单的计数器或更复杂的方法
        return "001"; // 例如，从001开始
    }
    
 // Employee ID: last two digits of the joining year + department ID + joining sequence
    public void createEmpId(Employee emp, Dept dept) {
        // Get the hiring date and extract the year
        LocalDate hirdate = emp.getHirdate();
        String year = Integer.toString(hirdate.getYear()-1911);
        
        // Get the department ID
        Integer deptId = dept.getDeptId();
        
        // Generate the sequence
        String sequence = generateSequence();
        
        // Construct the employee ID
        String employeeId = year + deptId.toString() + sequence;
        Employee employee = new Employee();
        // Set the employee ID and save the employee
        employee.setEmpId(Integer.parseInt(employeeId));
        
        empRepo.save(emp);
        
    }

	
	public void addEmp(Employee emp) {
		 empRepo.save(emp);
	}
	
	public Employee findById(Integer id) {
		Optional<Employee> optional = empRepo.findById(id);
	
		if(optional.isPresent()) {
			return optional.get();
			}
		return null;
	}
	
	public void deleteEmp(Integer id) {
		empRepo.deleteById(id);
	}
	
	public List<Employee> findAllEmp(){
		return empRepo.findAll();
		
	}
	
	public Employee updateEmp(Integer id,Employee newEmp) {
		Optional<Employee> optionalEmp = empRepo.findById(id);
		
		if(optionalEmp != null) {
			Employee oldEmp = optionalEmp.get();
			
			if(oldEmp!=newEmp) {
				empRepo.save(newEmp);
			}
		}
		return null;
	}
	
	
}
