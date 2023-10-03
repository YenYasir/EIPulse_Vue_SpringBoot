package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
=======
import jakarta.persistence.*;
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
import lombok.Data;

@Data
@Entity
<<<<<<< HEAD
@Table(name = "employee_permission")
public class EmployeePermission {
	@Id
	@Column(name = "emp_id")
	private int empId;
	@Id
	@Column(name = "permission_id")
	private int permissionId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id", insertable = false, updatable = false)
	@JsonIgnore
	private Employee employee;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permission_id", referencedColumnName = "permission_id", insertable = false, updatable = false)
=======
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
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
	@JsonIgnore
	private Permission permission;

}
<<<<<<< HEAD
=======

>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
