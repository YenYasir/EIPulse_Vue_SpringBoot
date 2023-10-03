package com.eipulse.teamproject.entitys;

import java.time.LocalDate;
//import java.util.Date;
import java.util.List;

import com.eipulse.teamproject.meetingroom.Reservations;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@Column(name = "emp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empId;
	@Column(name = "Id_number")
	private String idNumber;
	@Column(name = "emp_name")
	private String empName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "birth")
	private LocalDate birth;
	@Column(name = "photo", columnDefinition = "longblob", nullable = true)
	private byte[] photo;
	@Column(name = "phone")
	private String phone;
	@Column(name = "tel")
	private String tel;
	@Column(name = "address")
	private String address;
	@Column(name = "email")
	private String email;
	@Column(name = "emp_stat")
	private String empStat;
	@Column(name = "hire_date")
	private LocalDate hireDate;
	@Column(name = "leave_date", nullable = true)
	private LocalDate leaveDate;
	@Column(name = "edit_date")
	private LocalDate editDate;
	@Column(name = "dept_id")
	private Integer deptId;
	@Column(name = "title_id")
	private Integer titleId;
	@Column(name = "role_id")
	private Integer roleId;
	@Column(name = "line_id")
	private String lineId;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", referencedColumnName = " dept_id", insertable = false, updatable = false)
	private Dept dept;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", referencedColumnName = " title_id", insertable = false, updatable = false)
	private Title title;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = " role_id", insertable = false, updatable = false)
	private Role role;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	private List<EmployeePermission> permissions;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	private Login login;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Emergency> emergencies;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	private EmployeeInfo employeeInfo;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	private List<ClockTime> clockTime;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Reservations> reservations;
}
