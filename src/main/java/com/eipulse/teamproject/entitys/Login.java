package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;
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
    private String passWord;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    private Employee employee;

}
