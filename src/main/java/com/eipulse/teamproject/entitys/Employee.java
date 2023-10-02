package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;
    @Column(name = "id_number")
    private String idNumber;
    @Column(name = "emp_name")
    private String empName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birth")
    private LocalDate birth;
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "phone")
    private String phone;
    @Column(name = "tel")
    private String tel;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
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
    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    List<Attendance> attendance;
    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", idNumber='" + idNumber + '\'' +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", birth=" + birth +
                ", photo=" + Arrays.toString(photo) +
                ", phone='" + phone + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

