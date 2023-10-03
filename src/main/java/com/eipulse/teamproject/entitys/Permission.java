package com.eipulse.teamproject.entitys;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "permission")
public class Permission {
	@Id
	@Column(name = "permission_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permissionId;
	@Column(name = "grade")
	private String grade;
	@Column(name = "info")
	private String info;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "employeepermission",
//        joinColumns = {@JoinColumn(name = "permissionid")},
//        inverseJoinColumns = {@JoinColumn(name = "empid")})
//    private List<Employee> employees;
	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EmployeePermission> employeePermission;
}
