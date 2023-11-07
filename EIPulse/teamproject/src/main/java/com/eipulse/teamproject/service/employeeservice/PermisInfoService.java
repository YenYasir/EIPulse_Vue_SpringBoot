package com.eipulse.teamproject.service.employeeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.employee.Permission;
import com.eipulse.teamproject.entity.employee.PermissionInfo;
import com.eipulse.teamproject.entity.employee.PermissionMove;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.employeerepository.PermissionInfoRepository;
import com.eipulse.teamproject.repository.employeerepository.PermissionMoveRepository;
import com.eipulse.teamproject.repository.employeerepository.PermissionRepository;

@Service
public class PermisInfoService {

	private EmployeeRepository employeeRepository;
	private PermissionRepository permissionRepository;
	private PermissionInfoRepository permissionInfoRepository;
	private PermissionMoveRepository moveRepo;

	@Autowired
	public PermisInfoService(EmployeeRepository employeeRepository, PermissionRepository permissionRepository,
			PermissionInfoRepository permissionInfoRepository, PermissionMoveRepository moveRepo) {
		this.employeeRepository = employeeRepository;
		this.permissionRepository = permissionRepository;
		this.permissionInfoRepository = permissionInfoRepository;
		this.moveRepo = moveRepo;
	}

	// add

	public void addInfo(PermissionInfoDTO permissionInfoDTO) {
		//
		Employee emp = employeeRepository.findById(permissionInfoDTO.getEmpId()).get();
		Permission permis = permissionRepository.findById(permissionInfoDTO.getPermissionId()).get();

		permissionInfoRepository.save(new PermissionInfo(emp, permis));
	}

	// find once
	public PermissionInfoDTO findById(Integer empId) {
		PermissionInfo permissionInfo = permissionInfoRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException("查詢錯誤"));

		// 列出員工編號、權限編號、權限名稱
		return new PermissionInfoDTO(permissionInfo);
	}

	// find all
	public Page<PermissionInfoDTO> findAll(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return permissionInfoRepository.findAllEmpPermissionInfo(pageable);
	}

	// 查詢同部門的權限
	public Page<PermissionInfoDTO> findSameDept(Integer empId, Integer pageNumber) {
		Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("error"));
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return permissionInfoRepository.findSameDept(employee.getTitle().getDept().getDeptId(), pageable);
	}

	// 查詢同權限的人
	public Page<PermissionInfoDTO> findSamePermissionEmp(Integer empId, Integer pageNumber) {
		Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("error"));

		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return permissionInfoRepository
				.findSamePermissionEmp(employee.getPermissionInfo().getPermission().getPermissionId(), pageable);
	}

	// update info 的資料就會自動新增權限異動表的紀錄
	// 建立一個共用 DTO(permissionInfoDTO) 再各自把值分出來處理
	@Transactional
	public boolean updateAndLogChange(PermissionInfoDTO permissionInfoDTO) {
		PermissionMove permissionMove = new PermissionMove();
		// 查找和驗證員工資訊
		Employee emp = employeeRepository.findById(permissionInfoDTO.getEmpId())
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		permissionMove.setBeforePermissionName(emp.getPermissionInfo().getPermission().getPermissionName());

		// 查找和驗證舊 Permission 資料
		Permission newPermission = permissionRepository.findById(permissionInfoDTO.getPermissionId())
				.orElseThrow(() -> new RuntimeException("not found"));
		emp.getPermissionInfo().setPermission(newPermission);
		PermissionInfo newPermissionInfo = permissionInfoRepository.save(emp.getPermissionInfo());
		if (newPermissionInfo != null) {
			permissionMove.setEmp(emp);
			permissionMove.setApprover(permissionInfoDTO.getApprover());
			permissionMove.setAfterPermissionName(newPermission.getPermissionName());
			permissionMove.setReason(permissionInfoDTO.getReason());
			permissionMove.setEditDate(permissionInfoDTO.getEditDate());
			permissionMove.setEffectDate(permissionInfoDTO.getEffectDate());
			moveRepo.save(permissionMove);
			return true;
		}
		return false;
	}
}
