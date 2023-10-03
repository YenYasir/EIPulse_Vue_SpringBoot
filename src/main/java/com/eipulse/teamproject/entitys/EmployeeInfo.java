package com.eipulse.teamproject.entitys;

<<<<<<< HEAD
import jakarta.persistence.Column;
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
@Table(name = "employee_info")
public class EmployeeInfo {

	@Id
	@Column(name = "emp_id")
	private Integer empId;
	@Column(name = "dept_id")
	private int deptId;
	@Column(name = "title_id")
	private int titleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", referencedColumnName = "dept_id", insertable = false, updatable = false)
	private Dept dept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_id", referencedColumnName = "title_id", insertable = false, updatable = false)
	private Title title;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id", insertable = false, updatable = false)
	private Employee employee;
}
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	private Employee employee;

}
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
