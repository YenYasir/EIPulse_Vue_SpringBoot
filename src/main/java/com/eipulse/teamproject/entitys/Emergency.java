package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "emergency")
public class Emergency {

	@Id
	@Column(name = "emergency_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int emergencyId;
	@Column(name = "emergency_name")
	private String emergencyName;
	@Column(name = "emergency_phone")
	private String emergencyPhone;
	@Column(name = "emergency_relationship")
	private String emergencyRelationship;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	@JsonIgnore
	private Employee employee;

}
