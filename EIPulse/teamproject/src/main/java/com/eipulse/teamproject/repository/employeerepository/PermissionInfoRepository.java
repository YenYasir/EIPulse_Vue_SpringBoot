package com.eipulse.teamproject.repository.employeerepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO;
import com.eipulse.teamproject.entity.employee.PermissionInfo;

public interface PermissionInfoRepository extends JpaRepository<PermissionInfo, Integer> {

	// 查自己
	@Query("select new com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO(p) from PermissionInfo p where p.empId=?1")
	PermissionInfoDTO findByEmp(Integer empId);

	// 查全部
	@Query("select new com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO(p) from PermissionInfo p order by p.empId asc ")
	Page<PermissionInfoDTO> findAllEmpPermissionInfo(Pageable pageable);

	// 查同部門
	@Query("select new com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO(p) from PermissionInfo p where p.employee.title.dept.deptId =?1 order by p.permission.permissionId")
	Page<PermissionInfoDTO> findSameDept(Integer deptId, Pageable pageable);

	// 查同權限
	@Query("select new com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO(p) from PermissionInfo p where p.permission.permissionId=?1")
	Page<PermissionInfoDTO> findSamePermissionEmp(Integer permissionId, Pageable pageable);
}