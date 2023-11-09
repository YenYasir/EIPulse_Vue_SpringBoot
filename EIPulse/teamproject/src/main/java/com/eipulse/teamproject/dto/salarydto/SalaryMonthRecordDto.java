package com.eipulse.teamproject.dto.salarydto;

import com.eipulse.teamproject.entity.salaryentity.SalaryMonthRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SalaryMonthRecordDto {

	private Integer id;
	
	private Integer empId;
	
	private String empName;

	private Integer slYear;

	private Integer slMonth;

	private Integer addSum;

	private Integer deduSum;

	private Integer grossSalary;

	private Integer netSalary;
	

	private LocalDateTime createdDate;

	public SalaryMonthRecordDto() {
		// TODO Auto-generated constructor stub
	}
	
	public SalaryMonthRecordDto(SalaryMonthRecord record) {
		// TODO Auto-generated constructor stub
		this.id = record.getRecordId();
		this.empId = record.getEmployee().getEmpId();
		this.empName=record.getEmployee().getEmpName();
		this.slYear = record.getSlYear();
		this.slMonth = record.getSlMonth();
		this.addSum = record.getAddSum();
		this.deduSum = record.getDeduSum();
		this.netSalary = record.getNetSalary();
		this.createdDate = record.getCreatedDate();
	}
	
	public SalaryMonthRecordDto(SalaryMonthRecord record,String empName) {
		// TODO Auto-generated constructor stub
		this.id = record.getRecordId();
		this.empId = record.getEmployee().getEmpId();
		this.empName = empName;
		this.slYear = record.getSlYear();
		this.slMonth = record.getSlMonth();
		this.addSum = record.getAddSum();
		this.deduSum = record.getDeduSum();
		this.netSalary = record.getNetSalary();
		this.createdDate = record.getCreatedDate();
	}
	
	public SalaryMonthRecordDto(Integer empId,String empName,Integer year,Integer month,Integer addSum,Integer deduSum,Integer grossSalary,Integer netSalary) {
		// TODO Auto-generated constructor stub
		this.empId = empId;
		this.empName = empName;
		this.slYear = year;
		this.slMonth = month;
		this.addSum = addSum;
		this.deduSum = deduSum;
		this.grossSalary = grossSalary;
		this.netSalary = netSalary;
	}
}
