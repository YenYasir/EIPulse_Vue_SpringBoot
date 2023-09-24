package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.spirngboot3demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

	//更新查詢
		@Transactional
		@Modifying
		@Query(value="update Employee set name = :newStat where EmpId = :id")
		Integer updateStatByid(@Param("newStat") String newStat, @Param("id") Integer EmpId);

		@Transactional
		@Modifying
		@Query(value = "UPDATE Employee emp JOIN Dept dept ON emp.EmpId = dept.EmpId SET a.some_field = b.some_field", nativeQuery = true)
		Integer updateDeptByid(@Param("newStat") String newStat, @Param("id") Integer EmpId);
		
		
		
}
