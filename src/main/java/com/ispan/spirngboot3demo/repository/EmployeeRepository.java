package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.model.Title;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

	//HQL語法都是對應Entity的屬性，並不是table欄位
	//更新查詢
		@Transactional
		@Modifying
		@Query(value="update Employee set empStat = :newStat where empId = :id")
		Integer updateStatByid(@Param("newStat") String newStat, @Param("id") Integer EmpId);

		@Transactional
		@Modifying
		@Query(value = "UPDATE Employee set dept = :newdept where empId = :id")
		Integer updateDeptByid(@Param("newdept") Integer newdept, @Param("id") Integer EmpId);
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE Employee set title = :newtitle where empId = :id")
		Integer updateTitleByid(@Param("newtitle") Integer newtitle, @Param("id") Integer EmpId);
		
		
}
