package com.ispan.spirngboot3demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "Emergency")
public class Emergency {

	@Id
	@Column(name = "emergency_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer emergencyId;
	@Column(name = "emergency_name1")
	private String emerName1;
	
	@Column(name = "emergency_phone1")
	private String emerPhone1;
	
	@Column(name = "emergency_relationship1")
	private String emerRelationship1;
	
	@Column(name = "emergency_name2",nullable = true)
	private String emerName2;
	
	@Column(name = "emergency_phone2",nullable = true)
	private String emerPhone2;
	
	@Column(name = "emergency_relationship2",nullable = true)
	private String emerRelationship2;
	
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "emp_id", referencedColumnName = "empId")
	@JoinColumn(name = "emp_id")//, referencedColumnName = "emp_id")
	private Employee employee;


}
