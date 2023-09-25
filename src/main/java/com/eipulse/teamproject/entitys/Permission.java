package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission {
	@Id
	@Column(name = "permission_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permission_id;
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
