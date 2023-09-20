package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "permission")
public class Permission {
	@Id
	@Column(name = "PermissionId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permissionId;
	@Column(name = "Grade")
	private String grade;
	@Column(name = "Info")
	private String info;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "employeepermission",
//        joinColumns = {@JoinColumn(name = "permissionid")},
//        inverseJoinColumns = {@JoinColumn(name = "empid")})
//    private List<Employee> employees;
	@OneToMany(fetch = FetchType.LAZY)
	private List<EmployeePermission> employeePermission;
}
