package com.ispan.spirngboot3demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "employeeinfo")
public class EmployeeInfo {
		
	@Id
	@Column(name = "EmpId")
	private Integer empId;
	@Column(name = "DeptId")
	private int deptId;
	@Column(name = "TitleId")
	private int titleId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DeptId", referencedColumnName = "deptId",insertable = false, updatable = false)
	private Dept dept;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TitleId", referencedColumnName = "titleId",insertable = false, updatable = false)
	private Title title;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmpId", referencedColumnName = "empId",insertable = false, updatable = false)
	private Employee employee;
}