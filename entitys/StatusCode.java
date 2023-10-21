package com.eipulse.teamproject.entitys;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 這是表單狀態碼，顯示表單審核中、批准、退回等功能

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "status_code")
public class StatusCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer statusId;
	
	@Column(name="status")
	private String status;
	
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},mappedBy="status")
	private List<FormRecord> formRecord;
}
