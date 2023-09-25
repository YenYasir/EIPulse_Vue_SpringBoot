package com.ispan.spirngboot3demo.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name="EmpId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
    
    @Column(name="IdNumber")
    private String idNumber;
    
    @Column(name="EmpName")
    private String empName;
    
    @Column(name="Gender")
    private String gender;
       
    @Column(name="Birth")
    private LocalDate birth;
    
    @Column(name="Photo",columnDefinition = "longblob",nullable = true)
    private byte[] photo;
    
    @Column(name="Phone")
    private String phone;
    
    @Column(name="Tel")
    private String tel;
    
    @Column(name="Address")
    private String address;
    
    @Column(name="Email")
    private String email;
    
	@Column(name="EmpStat")
	private String empStat;
	
	@Column(name="Hirdate")
	private	LocalDate hirdate;
	
	@Column(name="LeaveDate",nullable = true)
	private	LocalDate leaveDate;
	
	@Column(name="EditDate")
	private	LocalDate editDate;
	
	@Column(name = "DeptId")
	private Integer deptId;
	
	@Column(name = "titleid")
	private Integer titleid;
	
	@Column(name = "role_id")
	private Integer role_id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DeptId", referencedColumnName = " deptId",insertable = false, updatable = false)
	private Dept dept;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TitleId", referencedColumnName = " titleid",insertable = false, updatable = false)
	private Title title;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = " role_id",insertable = false, updatable = false)
	private Role role;
	
	@OneToOne(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeInfo employeeInfo;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Emergency> emergencies;
}