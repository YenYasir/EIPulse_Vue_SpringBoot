package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "employeeinfo")
public class EmployeeInfo {
	
	
	@Column(name = "DeptId")
	private int deptId;
	@Column(name = "TitleId")
	private int titleId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DeptId", referencedColumnName = "deptid",insertable = false, updatable = false)
	private Dept dept;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TitleId", referencedColumnName = "titleid",insertable = false, updatable = false)
	private Title title;
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmpId", referencedColumnName = "empid")
	private Employee employee;

}
