package com.eipulse.teamproject.entitys;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
@Table(name = "clock_time")
public class ClockTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clock_time_id")
	private Integer clockTimeId;
	@Column(name = "time")
	private LocalDateTime time;
	@Column(name = "type")
	private String type;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "regions_id")
	private OfficeRegions officeRegions;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private Employee employee;

//    public String getEmployee() {
////                    三元運算子if !=null return  else  return null
//        return (this.employee !=null) ? this.employee.getEmpId() + this.employee.getEmpName():null;
//    }

	public ClockTime() {

	}

	public ClockTime(LocalDateTime time, String type, OfficeRegions officeRegions, Employee employee) {
		this.time = time;
		this.type = type;
		this.officeRegions = officeRegions;
		this.employee = employee;
	}

	public ClockTime(LocalDateTime time, String type, Employee employee) {
		this.time = time;
		this.type = type;
		this.employee = employee;
	}

}