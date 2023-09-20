package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "Dept")
public class Dept {
	@Id
	@Column(name = "DeptId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deptId;
	@Column(name = "DeptName")
	private String deptName;
	@Column(name = "DeptOffice")
	private String deptOffice;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dept", cascade = CascadeType.ALL)
	private List<EmployeeInfo> employeeInfo;


}
