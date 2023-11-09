package com.eipulse.teamproject.dto.salarydto;


import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SalaryDetailDto {
	
	private Integer id;
	
	private Integer empId;

	private String empName;
	
	private Integer slYear;
	
	private Integer slMonth;
	
	private Integer subjectId;
	
	private String subjectName;
	
	private String calculateType;
	
	private String frequency;

	private Integer amount;
	
	private Integer status;
	
	private Integer recordId;
	
	private LocalDateTime createdDate;
	
	
	public SalaryDetailDto(SalaryDetail detail) {
		this.id = detail.getId();
		this.empId = detail.getEmployee().getEmpId();
		this.empName =  detail.getEmployee().getEmpName();
		this.subjectId = detail.getSubjectType().getSubjectId();
		this.subjectName = detail.getSubjectType().getSubjectName();
		this.calculateType = detail.getSubjectType().getCalculateType();
		this.frequency = detail.getSubjectType().getFrequency();
		this.amount =  detail.getAmount();
		this.recordId = detail.getSalaryMonthRecord().getRecordId();
		this.createdDate = detail.getCreatedDate();
	}


	public SalaryDetailDto(DetailDto d) {
		this.amount=d.getAmount();
		this.subjectId=d.getSubjectId();
	}

	public SalaryDetailDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	

}
