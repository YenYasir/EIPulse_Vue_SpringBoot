package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Emergency")
public class Emergency {

	@Id
	@Column(name = "EmergencyId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int emergencyId;
	@Column(name = "EmergencyName")
	private String emergencyName;
	@Column(name = "EmergencyPhone")
	private String emergencyPhone;
	@Column(name = "EmergencyRelationship")
	private String emergencyRelationship;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmpId", referencedColumnName = "empid")
	private Employee employee;

}
