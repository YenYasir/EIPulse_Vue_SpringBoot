package com.eipulse.teamproject.repository.salaryrepository;

import com.eipulse.teamproject.dto.salarydto.SalaryInfoDto;
import com.eipulse.teamproject.entity.salaryentity.EmpSalaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;



public interface EmpSalaryInfoRepository extends JpaRepository<EmpSalaryInfo, Integer> {
	
	@Query(value ="select * from emp_salary_info where emp_id in (select emp_id from employee where hire_date <= :date)" ,nativeQuery = true)
	 List<EmpSalaryInfo> findInfoBeforeEndDate (@Param(value = "date") LocalDate date);
		
	
	Page<SalaryInfoDto> findAllBy(Pageable pageable, Class<SalaryInfoDto> dtoClass);
	// 名字模糊搜尋
	@Query(value = "SELECT * FROM emp_salary_info WHERE emp_id IN (SELECT emp_id FROM employee WHERE emp_name LIKE %:name%)", nativeQuery = true)
	 List<EmpSalaryInfo> findByEmpNameLike(@Param(value="name")String empName);
	
	// 分頁功能 by name
	
	
	@Query(value = "SELECT * FROM emp_salary_info WHERE emp_id IN (SELECT emp_id FROM employee WHERE emp_name LIKE %:name% )order by emp_id", nativeQuery = true)
    public Page<EmpSalaryInfo> findByNamePage(@Param(value="name") String name, Pageable pageable);

}
