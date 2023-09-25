package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee_permission")
public class EmployeePermission {
	@Id
	@Column(name = "emp_id")
	private int empId;
	@Id
	@Column(name = "permission_id")
	private int permissionId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id",insertable = false, updatable = false)
	@JsonIgnore
	private Employee employee;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permission_id", referencedColumnName = "permission_id",insertable = false, updatable = false)
	@JsonIgnore
	private Permission permission;

}

