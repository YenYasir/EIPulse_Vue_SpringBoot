package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name="EmpId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;
    @Column(name="IdNumber")
    private String idNumber;
    @Column(name="EmpName")
    private String empName;
    @Column(name="Gender")
    private String gender;
    @Column(name="Birth")
    private String birth;
    @Column(name="Photo")
    private byte[] photo;
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "EmployeePermission",
//        joinColumns = {@JoinColumn(name = "EmpId")},
//        inverseJoinColumns = {@JoinColumn(name = "PermissionId")})
//    private List<Permission> permissions;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeePermission> permissions;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
	private Login login;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Emergency> emergencies;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeInfo employeeInfo;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
	private Contact contact;

//	忽略關聯查詢，避免出現多餘資料例如關聯的table
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "employee",cascade = CascadeType.ALL)
	private List<ClockTime> clockTime;


}

