package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EmployeePermission")
public class EmployeePermission {
	@Id
	@Column(name = "EmpId")
	private int empId;
	@Id
	@Column(name = "PermissionId")
	private int permissionId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmpId", referencedColumnName = "empid",insertable = false, updatable = false)
	private Employee employee;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PermissionId", referencedColumnName = "permissionId",insertable = false, updatable = false)
	private Permission permission;

}

