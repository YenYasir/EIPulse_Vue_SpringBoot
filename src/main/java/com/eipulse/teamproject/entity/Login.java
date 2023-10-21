package com.eipulse.teamproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "login")
public class Login {
	@Id
	@Column(name="emp_id")
	private int empId;
	
	@Column(name="password")
    private String password;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    private Employee employee;

    @Override
    public String toString() {
        return "Login{" +
                "empId=" + empId +
                ", passWord='" + password + '\'' +
                ", employee=" + employee +
                '}';
    }
}
