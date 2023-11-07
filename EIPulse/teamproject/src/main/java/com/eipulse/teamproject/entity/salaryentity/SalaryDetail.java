package com.eipulse.teamproject.entity.salaryentity;

import java.time.LocalDateTime;

import com.eipulse.teamproject.dto.salarydto.DetailDto;
import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salary_detail", schema = "eipulseproject")
public class SalaryDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@JsonBackReference(value = "employee-salary-detail")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "emp_id", nullable = false)
	private Employee employee;

	@JsonBackReference(value = "subject-type-detail")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id", nullable = false)
	private SubjectType subjectType;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "created_date" ,insertable = false,updatable = false)
	private LocalDateTime createdDate;


	@JsonBackReference(value = "salary-month-record-detail")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id", nullable = false)
	private SalaryMonthRecord salaryMonthRecord;


	public SalaryDetail() {
	}
	
	
	public SalaryDetail(DetailDto detailDto) {
		this.id=detailDto.getId();

		Employee employee = new Employee();
		employee.setEmpId(detailDto.getEmpId());
		employee.setEmpName(detailDto.getEmpName());
		this.employee = employee;

		SubjectType subject = new SubjectType();
		subject.setSubjectId(detailDto.getSubjectId());
		subject.setSubjectName(detailDto.getEmpName());
		this.subjectType = subject;

		this.amount = detailDto.getAmount();

		SalaryMonthRecord salaryMonthRecord = new SalaryMonthRecord();
		salaryMonthRecord.setRecordId(detailDto.getRecordId());
		this.salaryMonthRecord = salaryMonthRecord;
		

	}

}