package com.eipulse.teamproject.entitys;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "Login")
public class Login {
	@Id
	@Column(name="EmpId")
	private Integer empId;
	
	@Column(name="PassWord")
    private String passWord;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmpId", referencedColumnName = "empid")
    private Employee employee;

}
