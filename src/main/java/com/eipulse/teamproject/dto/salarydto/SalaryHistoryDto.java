package com.eipulse.teamproject.dto.salarydto;

import java.time.LocalDateTime;

import com.eipulse.teamproject.entity.salaryentity.SalaryHistory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryHistoryDto {
	
		private Integer id;
	        
	    private Integer empId;

	    private String empName;

	    private Integer originalSalary;
	    
	    private Integer adjustSalary;

	    private String remark;

	    private LocalDateTime adjustedDate;
	    
	    public SalaryHistoryDto(SalaryHistory history) {
	    	this.id = history.getId();
	    	this.empId = history.getEmpId();
	    	this.empName = history.getEmployee().getEmpName();
	    	this.originalSalary =  history.getOriginalSalary();
	    	this.adjustSalary = history.getAdjustSalary();
	    	this.remark =history.getRemark();
	    	this.adjustedDate = history.getAdjustedDate();
	    }
	    
	    public SalaryHistoryDto(SalaryHistory history,String empName) {
	    	this.id = history.getId();
	    	this.empId = history.getEmpId();
	    	this.empName = empName;
	    	this.originalSalary =  history.getOriginalSalary();
	    	this.adjustSalary = history.getAdjustSalary();
	    	this.remark =history.getRemark();
	    	this.adjustedDate = history.getAdjustedDate();
	    }

	    public SalaryHistoryDto(Integer empId,Integer adjustSalary,String remark )
	    {}
	    
		public SalaryHistoryDto() {
			super();
		}
	    
	    }
