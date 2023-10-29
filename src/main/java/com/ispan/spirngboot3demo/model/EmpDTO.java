package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer titleId;
    private String titleName;
    private String empState;
    private LocalDate hireDate;
    private LocalDate leaveDate;
    private LocalDate editDate;


    public EmpDTO() {
    }

    public EmpDTO(Integer empId,String empName, LocalDate birth, String email, String idNumber, String gender, String phone, String tel, String photoUrl, String address, Integer titleId,LocalDate hireDate, String empState) {
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


    public EmpDTO(Integer empId, String empName, LocalDate birth, String email, String idNumber, String gender, String phone, String tel, String photoUrl, String address, String titleName, String empState) {
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

    public EmpDTO(Integer empId, String empName, LocalDate birth, String email, String idNumber, String gender, String phone, String tel, String photoUrl, String address,String titleName, String empState, LocalDate hireDate, LocalDate leaveDate, LocalDate editDate) {
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
    }


    public EmpDTO(Employee employee) {
    }
}
