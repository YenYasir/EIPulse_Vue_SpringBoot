package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.dto.employeedto.EmpDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    @Query("from Employee e where e.email=?1")
     Optional<Employee> findByEmail(String email);

    
    @Query("select new com.eipulse.teamproject.dto.employeedto.EmpDTO(e) from Employee e where e.title.dept.deptId=?1")
    List<EmpDTO> findSameDeptEmp(Integer deptId);

}