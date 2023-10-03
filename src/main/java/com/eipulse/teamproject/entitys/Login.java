package com.eipulse.teamproject.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {
	@Id
	@Column(name = "emp_id")
	private Integer empId;

	@Column(name = "pass_word")
	private String passWord;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private Employee employee;

}
