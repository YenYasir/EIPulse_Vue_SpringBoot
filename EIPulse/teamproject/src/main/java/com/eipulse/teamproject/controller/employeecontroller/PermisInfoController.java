package com.eipulse.teamproject.controller.employeecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.dto.employeedto.PermissionInfoDTO;
import com.eipulse.teamproject.service.employeeservice.PermisInfoService;

@RestController
public class PermisInfoController {

	private PermisInfoService infoService;

	@Autowired
	public PermisInfoController(PermisInfoService infoService) {
		this.infoService = infoService;
	}

	// add info
	@PostMapping("/permissionInfo")
	public ResponseEntity<?> add(@RequestBody PermissionInfoDTO infoDTO) {
		infoService.addInfo(infoDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 查詢單筆
	@GetMapping("/permissionInfo/{empId}")
	public ResponseEntity<?> findById(@PathVariable Integer empId) {
		return new ResponseEntity<>(infoService.findById(empId), HttpStatus.OK);
	}

	// 查詢全部
	@GetMapping("/permissionInfo/findAll/{pageNumber}")
	public ResponseEntity<?> findAll(@PathVariable Integer pageNumber) {
		return new ResponseEntity<>(infoService.findAll(pageNumber), HttpStatus.OK);
	}

	// 查同部門
	@GetMapping("/permissionInfo/{empId}/{pageNumber}")
	public ResponseEntity<?> findSameDept(@PathVariable Integer empId, @PathVariable Integer pageNumber) {
		return new ResponseEntity<>(infoService.findSameDept(empId, pageNumber), HttpStatus.OK);
	}

	// 查同權限
	@GetMapping("/permissionInfo/permission/{empId}/{pageNumber}")
	public ResponseEntity<?> findSamePermissionEmp(@PathVariable Integer empId, @PathVariable Integer pageNumber) {
		return new ResponseEntity<>(infoService.findSamePermissionEmp(empId, pageNumber), HttpStatus.OK);
	}

	// Update permission info
	@PutMapping("/permissionInfo")
	public ResponseEntity<?> update(@RequestBody PermissionInfoDTO permissionInfoDTO) {
		return new ResponseEntity<>(infoService.updateAndLogChange(permissionInfoDTO), HttpStatus.OK);
	}
}
