package com.eipulse.teamproject.entitys;

import java.util.List;

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
@Table(name = "dept")
public class Dept {
	@Id
	@Column(name = "dept_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deptId;
	@Column(name = "dept_name")
	private String deptName;
	@Column(name = "dept_office")
	private String deptOffice;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", referencedColumnName = "dept_id")
	private List<EmployeeInfo> employeeInfo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private Employee employee;
}
