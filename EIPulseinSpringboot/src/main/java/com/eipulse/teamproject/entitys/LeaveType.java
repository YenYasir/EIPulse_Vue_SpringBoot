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
@Table(name = "dayoff_type")
public class LeaveType {

	@Id
    @Column(name="type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeId;
	
	@Column(name="type_name")
	private String typeName;
	
	@Column(name="off_days")
	private Integer offDays;
	
	
	
}
