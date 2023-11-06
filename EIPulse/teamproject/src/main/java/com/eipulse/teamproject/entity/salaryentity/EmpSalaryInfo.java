package com.eipulse.teamproject.entity.salaryentity;
import com.eipulse.teamproject.dto.salarydto.SalaryInfoDto;
import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "emp_salary_info", schema = "finalproject")
public class EmpSalaryInfo {
	@Id
    @Column(name = "emp_id", insertable =false,updatable=false)
    private Integer empId;
	
	@Transient // 告诉JPA忽略此字段，不要映射到数据库
	private String empName;

    @Column(name = "basic_salary", nullable = false)
    private Integer basicSalary;
 
    @Column(name = "labor_insurance_grade", nullable = false)
    private Integer laborInsuranceGrade;

    @Column(name = "labor_volunteer_pension_rate", nullable = false)
    private Double laborVolunteerPensionRate;
    
    @Column(name = "health_insurance_grade", nullable = false)
    private Integer healthInsuranceGrade;

    @Column(name = "family_dependants_num")
    private Integer familyDependantsNum;
   
    @Column(name = "welfare_benefits_deduction", length = 5)
    private String welfareBenefitsDeduction;

	@JsonBackReference(value = "employee-salary-info")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;
    
    
	public EmpSalaryInfo() {
	
	}


	public EmpSalaryInfo(Integer basicSalary) {
		super();
		this.basicSalary = basicSalary;
	}


	public EmpSalaryInfo(Integer basicSalary, Integer laborInsuranceGrade, Double laborVolunteerPensionRate,
			Integer healthInsuranceGrade, Integer familyDependantsNum, String welfareBenefitsDeduction) {
		super();
		this.basicSalary = basicSalary;
		this.laborInsuranceGrade = laborInsuranceGrade;
		this.laborVolunteerPensionRate = laborVolunteerPensionRate;
		this.healthInsuranceGrade = healthInsuranceGrade;
		this.familyDependantsNum = familyDependantsNum;
		this.welfareBenefitsDeduction = welfareBenefitsDeduction;
	}


	public EmpSalaryInfo(SalaryInfoDto infoDto) {
		super();
		this.empId = infoDto.getEmpId();
		this.empName=infoDto.getEmpName();
		this.basicSalary = infoDto.getBasicSalary();
		this.laborInsuranceGrade =infoDto.getLaborInsuranceGrade();
		this.laborVolunteerPensionRate =infoDto.getLaborVolunteerPensionRate();
		this.healthInsuranceGrade = infoDto.getHealthInsuranceGrade();
		this.familyDependantsNum = infoDto.getFamilyDependantsNum();
		this.welfareBenefitsDeduction = infoDto.getWelfareBenefitsDeduction();

	}
	
	
	
	

}
