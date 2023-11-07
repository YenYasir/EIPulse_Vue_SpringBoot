package com.eipulse.teamproject.repository.employeerepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eipulse.teamproject.entity.employee.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer> {

	// 模糊搜尋部門名稱
	@Query("FROM Dept d WHERE d.deptName LIKE %?1% ORDER BY d.deptId")
	public Page<Dept> findByDeptNamePage(@Param("name") String name, Pageable pageable);

	// 分頁功能
	@Query("FROM Dept d WHERE d.deptId > :id ORDER BY d.deptId")
	public Page<Dept> findByPage(@Param("id") Integer id, Pageable pageable);

}