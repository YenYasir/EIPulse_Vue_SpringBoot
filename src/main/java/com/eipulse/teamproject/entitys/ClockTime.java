package com.eipulse.teamproject.entitys;

import java.time.LocalDateTime;

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
import lombok.Data;

@Data
@Entity
@Table(name = "clocktime")
public class ClockTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clocktime_id")
	private Integer clockTimeId;
	@Column(name = "emp_id")
	private Integer empId;
	@Column(name = "clocktime")
	private LocalDateTime clocktime;
	@Column(name = "clocktime_type")
	private String clocktimeType;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id", insertable = false, updatable = false)
	private Employee employee;

	public ClockTime() {
	}

	public ClockTime(Integer clockTimeId, Integer empId, LocalDateTime clocktime, String clocktimeType,
			Employee employee) {
		this.clockTimeId = clockTimeId;
		this.empId = empId;
		this.clocktime = clocktime;
		this.clocktimeType = clocktimeType;
		this.employee = employee;
	}

	public ClockTime(Integer empId, LocalDateTime clocktime, String clocktimeType) {
		this.empId = empId;
		this.clocktime = clocktime;
		this.clocktimeType = clocktimeType;
	}

}