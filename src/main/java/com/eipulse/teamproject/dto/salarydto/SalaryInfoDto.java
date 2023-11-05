package com.eipulse.teamproject.dto.salarydto;

import com.eipulse.teamproject.entity.salaryentity.EmpSalaryInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SalaryInfoDto {
	
	private Integer empId;
	
	private String empName;
	
//	給薪資異動用
	private LocalDate hireDate;
	
	private Integer basicSalary;
	
	private Integer laborInsuranceGrade;
	
	private Double laborVolunteerPensionRate;
	
	private Integer healthInsuranceGrade;
	
	private Integer familyDependantsNum;
	
	private String welfareBenefitsDeduction;

		
	//從資料庫取值用
	public SalaryInfoDto(EmpSalaryInfo info) {
		this.empId =info.getEmpId();
		this.empName = info.getEmployee().getEmpName();
		this.basicSalary = info.getBasicSalary();
		this.laborInsuranceGrade=info.getLaborInsuranceGrade();
		this.laborVolunteerPensionRate = info.getLaborVolunteerPensionRate();
		this.healthInsuranceGrade = info.getHealthInsuranceGrade();
		this.familyDependantsNum = info.getFamilyDependantsNum();
		this.welfareBenefitsDeduction = info.getWelfareBenefitsDeduction();
	}


	public SalaryInfoDto(EmpSalaryInfo info,String empName,LocalDate hiredate) {	
		this.empId =info.getEmpId();
		this.empName = empName;
		this.hireDate =hiredate;
		this.basicSalary = info.getBasicSalary();
		this.laborInsuranceGrade=info.getLaborInsuranceGrade();
		this.laborVolunteerPensionRate = info.getLaborVolunteerPensionRate();
		this.healthInsuranceGrade = info.getHealthInsuranceGrade();
		this.familyDependantsNum = info.getFamilyDependantsNum();
		this.welfareBenefitsDeduction = info.getWelfareBenefitsDeduction();
	}
	public SalaryInfoDto() {
		super();
	}
	
	}
 




