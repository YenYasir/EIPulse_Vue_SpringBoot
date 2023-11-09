package com.eipulse.teamproject.entity.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission_info")
public class PermissionInfo {
	@Id
	@Column(name = "emp_id", nullable = false)
	private Integer empId;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "emp_id", nullable = false)
	@JsonBackReference(value = "emp-permissionInfo")
	private Employee employee;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "permission_id", nullable = false)
	private Permission permission;

	public PermissionInfo(Integer empId, Permission permission) {
		this.empId = empId;
		this.permission = permission;
	}

	public PermissionInfo(Employee employee, Permission permission) {
		this.employee = employee;
		this.permission = permission;
	}

	public PermissionInfo() {
	}
}