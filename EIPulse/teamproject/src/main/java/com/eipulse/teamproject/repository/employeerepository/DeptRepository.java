package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.dto.employeedto.DeptDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eipulse.teamproject.entity.employee.Dept;

import java.util.List;

public interface DeptRepository extends JpaRepository<Dept, Integer> {

	// 模糊搜尋部門名稱
	@Query("FROM Dept d WHERE d.deptName LIKE %?1% ORDER BY d.deptId")
	public Page<Dept> findByDeptNamePage(@Param("name") String name, Pageable pageable);

	// 過濾部門的其他關聯
	@Query("select new com.eipulse.teamproject.dto.employeedto.DeptDTO(d) from Dept d ")
	List<DeptDTO> findAllDetp();

//	@Query("select new com.eipulse.teamproject.dto.employeedto.DeptDTO(d) from Dept d  ")
//	List<DeptDTO> findAllDetpEmp();


}