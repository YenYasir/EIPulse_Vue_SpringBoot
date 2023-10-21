package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dept")
public class Dept {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deptId;
	
	@Column(name = "dept_name")
	private String deptName;
	
	@Column(name = "dept_office")
	private String deptOffice;
	
	 @OneToOne
	 @JoinColumn(name = "dept_manager", referencedColumnName = "emp_id")
	 private Employee deptManager;



}
