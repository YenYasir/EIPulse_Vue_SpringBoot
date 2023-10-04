package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee_info")
public class EmployeeInfo {

	@Column(name = "dept_id")
	private int deptId;
	@Column(name = "Title_id")
	private int titleId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", referencedColumnName = "dept_id", insertable = false, updatable = false)
	private Dept dept;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", referencedColumnName = "title_id", insertable = false, updatable = false)
	private Title title;
	@Id
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private Employee employee;

}
