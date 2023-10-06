package com.ispan.spirngboot3demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permission_info")
public class PermissionInfo {
		
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
//	@Column(name = "dept_id")
//	private Integer deptId;
//	@Column(name = "title_id")
//	private Integer titleId;
//	@Column(name = "title_name")
//	private String  titleName;
//	@Column(name = "role_id")
//	private Integer roleId;
	@Column(name = "role_name")
	private String  roleName;

	@Column(name="title_name")
	private String titleName;
//	@Column(name = "emp_id")
//	private Integer empId;

	
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "dept_id", referencedColumnName = "deptId",insertable = false, updatable = false)
	@JoinColumn(name = "dept_id")//, referencedColumnName = "dept_id",insertable = false, updatable = false)
	private Dept dept;
	
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "title_id", referencedColumnName = "titleId",insertable = false, updatable = false)
	@JoinColumn(name = "title_id")//, referencedColumnName = "title_id",insertable = false, updatable = false)
	private Title title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id",insertable = false, updatable = false)//,referencedColumnName ="role_id" )
	private Role role;

	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "emp_id", referencedColumnName = "empId",insertable = false, updatable = false)
	@JoinColumn(name = "emp_id")//, referencedColumnName = "emp_id",insertable = false, updatable = false)
	private Employee employee;
}