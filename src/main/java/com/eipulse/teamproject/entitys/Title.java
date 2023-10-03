package com.eipulse.teamproject.entitys;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "title")
public class Title {
	@Id
	@Column(name = "title_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int titleId;
	@Column(name = "title_name")
	private String titleName;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "title", cascade = CascadeType.ALL)
	private List<EmployeeInfo> employeeInfo;

}
