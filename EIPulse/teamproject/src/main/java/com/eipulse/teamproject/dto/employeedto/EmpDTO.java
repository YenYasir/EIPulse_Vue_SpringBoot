package com.eipulse.teamproject.dto.employeedto;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpDTO {
	private Integer empId;
	private String empName;
	private LocalDate birth;
	private String email;
	private String idNumber;
	private String gender;
	private String phone;
	private String tel;
	private String photoUrl;
	private String address;

	private Integer titleId;
	private String titleName;
	private String empState;
	private LocalDate hireDate;
	private LocalDate leaveDate;
	private LocalDate editDate;
	private String password;
	private String newPassword;
	private Set<Integer> permissionId = new LinkedHashSet<>();

	private Integer otpCheck;

	public EmpDTO() {
	}

	public EmpDTO(Integer empId, String empName, LocalDate birth, String email, String idNumber, String gender,
			String phone, String tel, String photoUrl, String address, Integer titleId, LocalDate hireDate,
			String empState) {
		this.empId = empId;
		this.empName = empName;
		this.birth = birth;
		this.email = email;
		this.idNumber = idNumber;
		this.gender = gender;
		this.phone = phone;
		this.tel = tel;
		this.photoUrl = photoUrl;
		this.address = address;
		this.titleId = titleId;
		this.hireDate = hireDate;
		this.empState = empState;
	}

	public EmpDTO(String empName, String email, String phone, String tel, String address) {
		this.empName = empName;
		this.email = email;
		this.phone = phone;
		this.tel = tel;
		this.address = address;
	}

	public EmpDTO(Integer empId, String empName, LocalDate birth, String email, String idNumber, String gender,
			String phone, String tel, String photoUrl, String address, String titleName, String empState) {
		this.empId = empId;
		this.empName = empName;
		this.birth = birth;
		this.email = email;
		this.idNumber = idNumber;
		this.gender = gender;
		this.phone = phone;
		this.tel = tel;
		this.photoUrl = photoUrl;
		this.address = address;
		this.titleName = titleName;
		this.empState = empState;
	}

	public EmpDTO(Integer empId, String empName, LocalDate birth, String email, String idNumber, String gender,
			String phone, String tel, String photoUrl, String address, String titleName, String empState,
			LocalDate hireDate, LocalDate leaveDate, LocalDate editDate, String password) {
		this.empId = empId;
		this.empName = empName;
		this.birth = birth;
		this.email = email;
		this.idNumber = idNumber;
		this.gender = gender;
		this.phone = phone;
		this.tel = tel;
		this.photoUrl = photoUrl;
		this.address = address;
		this.titleName = titleName;
		this.empState = empState;
		this.hireDate = hireDate;
		this.leaveDate = leaveDate;
		this.editDate = editDate;
		this.password = password;
	}

	public EmpDTO(Employee employee) {
		this.empId = employee.getEmpId();
		this.empName = employee.getEmpName();
		this.titleName = employee.getTitle().getTitleName();
	}

	public EmpDTO(Integer empId, String password) {
		this.empId = empId;
		this.password = password;
	}

}
