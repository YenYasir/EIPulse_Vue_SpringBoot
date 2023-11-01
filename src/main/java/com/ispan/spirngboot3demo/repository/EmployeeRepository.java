package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.EmpDTO;
import com.ispan.spirngboot3demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // 模糊收尋員工姓名
    @Query("FROM Employee emp WHERE emp.empName LIKE %?1% ")
    List<EmpDTO> findByNameLikeSearch (String name);

    // 分頁功能
    @Query("SELECT emp FROM Employee emp WHERE emp.empId > :id ORDER BY emp.empId")
    public Page<Employee> findByEmpIdPage(@Param("id") Integer id, Pageable pageable);

    // 分頁功能 by name
    @Query("SELECT emp FROM Employee emp WHERE emp.empName Like %?1% ORDER BY emp.empId")
    public Page<Employee> findByNamePage(@Param("name") String name, Pageable pageable);

}