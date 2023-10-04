package com.eipulse.teamproject.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "login")
public class Login {
	@Id
	@Column(name = "emp_id")
	private int empId;

	@Column(name = "password")
	private String password;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private Employee employee;

	@Override
	public String toString() {
		return "Login{" + "empId=" + empId + ", passWord='" + password + '\'' + ", employee=" + employee + '}';
	}
}
