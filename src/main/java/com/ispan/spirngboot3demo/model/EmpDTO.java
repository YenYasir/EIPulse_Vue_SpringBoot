package com.ispan.spirngboot3demo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class EmpDTO {
    private Integer id;
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
    private String empState;


    public EmpDTO() {
    }

    public EmpDTO(Integer id,String empName, LocalDate birth, String email, String idNumber, String gender, String phone, String tel, String photoUrl, String address, Integer titleId, String empState) {
        this.id = id;
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
        this.empState = empState;
    }

    public EmpDTO(String empName, String email, String phone, String tel, String address) {
        this.empName = empName;
        this.email = email;
        this.phone = phone;
        this.tel = tel;
        this.address = address;
    }
}
