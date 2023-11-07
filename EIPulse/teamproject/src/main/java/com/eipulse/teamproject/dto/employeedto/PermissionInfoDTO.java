package com.eipulse.teamproject.dto.employeedto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.eipulse.teamproject.entity.employee.PermissionInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionInfoDTO {

	private Integer id;
	private Integer empId;
	private String empName;
	private Integer permissionId;
	private String permissionName;
	private String beforePermissionName;
	private String afterPermissionName;
	private String reason;
	private LocalDate effectDate;
	private String approver;
	private LocalDateTime editDate;
	private String permissionStatement;

	public PermissionInfoDTO() {
	}

	public PermissionInfoDTO(PermissionInfo permissionInfo) {
		this.empId = permissionInfo.getEmpId();
		this.empName = permissionInfo.getEmployee().getEmpName();
		this.permissionId = permissionInfo.getPermission().getPermissionId();
		this.permissionName = permissionInfo.getPermission().getPermissionName();
		this.permissionStatement = permissionInfo.getPermission().getPermissionStatement();
	}

}
