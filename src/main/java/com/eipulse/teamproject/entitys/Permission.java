package com.eipulse.teamproject.entitys;

<<<<<<< HEAD
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
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01

@Data
@Entity
@Table(name = "permission")
public class Permission {
	@Id
<<<<<<< HEAD
	@Column(name = "permission_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permissionId;
	@Column(name = "grade")
	private String grade;
	@Column(name = "info")
=======
	@Column(name = "PermissionId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permissionId;
	@Column(name = "Grade")
	private String grade;
	@Column(name = "Info")
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
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
