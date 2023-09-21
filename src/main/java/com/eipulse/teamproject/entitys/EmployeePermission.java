package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	private Employee employee;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PermissionId", referencedColumnName = "permissionId",insertable = false, updatable = false)
	@JsonIgnore
	private Permission permission;

}

