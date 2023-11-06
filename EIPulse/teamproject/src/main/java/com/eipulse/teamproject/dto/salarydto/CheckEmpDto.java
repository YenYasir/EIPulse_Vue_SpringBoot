package com.eipulse.teamproject.dto.salarydto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckEmpDto {
	
	private Integer empId;
	
	private String empName;
	
	private Boolean status;
	
	
	public CheckEmpDto(Integer empId,String empName,Boolean status) {
		this.empId =empId;
		this.empName=empName;
		this.status=status;
	}
	
	public CheckEmpDto() {
		// TODO Auto-generated constructor stub
	}

}
