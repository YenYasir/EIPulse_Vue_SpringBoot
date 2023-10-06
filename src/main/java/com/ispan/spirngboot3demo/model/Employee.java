package com.ispan.spirngboot3demo.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name="emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
    
    @Column(name="id_number")
    private String idNumber;
    
    @Column(name="emp_name")
    private String empName;
    
    @Column(name="gender")
    private String gender;
       
    @Column(name="birth")
    private LocalDate birth;
    
    @Column(name="photo",columnDefinition = "longblob",nullable = true)
    private byte[] photo;
    
    @Column(name="phone")
    private String phone;
    
    @Column(name="tel")
    private String tel;
    
    @Column(name="address")
    private String address;
    
    @Column(name="email")
    private String email;
    
	@Column(name="emp_stat")
	private String empStat;
	
	@Column(name="hirdate")
	private	LocalDate hirdate;
	
	@Column(name="leave_date",nullable = true)
	private	LocalDate leaveDate;
	
	@Column(name="edit_date")
	private	LocalDate editDate;

	@Column(name = "dept_id")
	private Integer deptId;

	@Column(name = "title_id")
	private Integer titleId;

	@Column(name = "role_id")
	private Integer roleId;

	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "dept_id", referencedColumnName = "deptId",insertable = false, updatable = false)
	@JoinColumn(name = "dept_id",insertable = false, updatable = false)
	private Dept dept;
	
	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "title_id", referencedColumnName = "titleId",insertable = false, updatable = false)
	@JoinColumn(name = "title_id",insertable = false, updatable = false)
	private Title title;
	
	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "role_id", referencedColumnName = "roleId",insertable = false, updatable = false)
	@JoinColumn(name = "role_id",insertable = false, updatable = false)
	private Role role;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private PermissionInfo permissionInfo;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Emergency> emergencies;
}