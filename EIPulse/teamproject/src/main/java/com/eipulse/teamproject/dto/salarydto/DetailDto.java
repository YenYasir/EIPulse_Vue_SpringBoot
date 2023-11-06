package com.eipulse.teamproject.dto.salarydto;

import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailDto {
	private Integer id;
	private Integer empId;
	private Integer subjectId;
	private String  subjectName;
	private String calculateType;
	private String empName;
	private Integer amount;
	private Integer recordId;
	
	public DetailDto(Integer empId,Integer subjectId,Integer amount) {
	this.empId=empId;
		this.subjectId =subjectId;
		this.amount=amount;
		
	}
	public DetailDto(SalaryDetail salaryDetail) {
		this.id = salaryDetail.getId();
		this.empId = salaryDetail.getEmployee().getEmpId();
		this.empName = salaryDetail.getEmployee().getEmpName();
		this.subjectId = salaryDetail.getSubjectType().getSubjectId();
		this.subjectName = salaryDetail.getSubjectType().getSubjectName();
		this.calculateType = salaryDetail.getSubjectType().getCalculateType();
		this.amount = salaryDetail.getAmount();
		this.recordId = salaryDetail.getSalaryMonthRecord().getRecordId();
	}

	public DetailDto() {
		// TODO Auto-generated constructor stub
	}

}
