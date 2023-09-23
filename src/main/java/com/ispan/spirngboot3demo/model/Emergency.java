package com.ispan.spirngboot3demo.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Emergency")
public class Emergency {

	@Id
	@Column(name = "EmergencyId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer emergencyId;
	@Column(name = "EmergencyName1")
	private String emerName1;
	
	@Column(name = "EmergencyPhone1")
	private String emerPhone1;
	
	@Column(name = "EmergencyRelationship1")
	private String emerRelationship1;
	
	@Column(name = "EmergencyName2",nullable = true)
	private String emerName2;
	
	@Column(name = "EmergencyPhone2",nullable = true)
	private String emerPhone2;
	
	@Column(name = "EmergencyRelationship2",nullable = true)
	private String emerRelationship2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmpId", referencedColumnName = "empid")
	private Employee employee;

}
