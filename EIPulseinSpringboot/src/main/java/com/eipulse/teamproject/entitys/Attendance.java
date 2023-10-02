package com.eipulse.teamproject.entitys;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="attendance")
public class Attendance {
	
	@Id
    @Column(name="id")
	private Integer id;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd") 
	@Temporal(TemporalType.DATE)
	@Column(name = "work_date")
	private Date workDate;
	
	@Column(name="emp_id")
	private Integer empId;
	
	@Column(name="on_work")
	private Timestamp onWork;
	
	@Column(name="off_work")
	private Timestamp offWork;
	
	@Column(name="status")
	private String status;//定義是否正常上下班or遲到or早退
	
	
}
