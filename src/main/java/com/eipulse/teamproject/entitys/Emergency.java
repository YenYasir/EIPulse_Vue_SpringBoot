package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private Employee employee;

}
