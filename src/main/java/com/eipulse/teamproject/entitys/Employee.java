package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
//import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "EmpId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;
    @Column(name = "IdNumber")
    private String idNumber;
    @Column(name = "EmpName")
    private String empName;
    @Column(name = "Gender")
    private String gender;
    @Column(name = "Birth")
    private LocalDate birth;
    @Column(name = "Photo",columnDefinition = "longblob")
    private byte[] photo;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Tel")
    private String tel;
    @Column(name = "Address")
    private String address;
    @Column(name = "Email")
    private String email;
    //    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "EmployeePermission",
//        joinColumns = {@JoinColumn(name = "EmpId")},
//        inverseJoinColumns = {@JoinColumn(name = "PermissionId")})
//    private List<Permission> permissions;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeePermission> permissions;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private Login login;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Emergency> emergencies;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeInfo employeeInfo;
    //	忽略關聯查詢，避免出現多餘資料例如關聯的table
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ClockTime> clockTime;


}

