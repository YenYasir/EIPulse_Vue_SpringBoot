package com.ispan.spirngboot3demo.model;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Dept")
public class Dept {
	@Id
	@Column(name = "DeptId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deptId;
	@Column(name = "DeptName")
	private String deptName;
	@Column(name = "DeptOffice")
	private String deptOffice;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dept", cascade = CascadeType.ALL)
	private List<EmployeeInfo> employeeInfo;
}
