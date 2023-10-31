package com.ispan.spirngboot3demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employee", schema = "new_eipulse")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "emp_name", nullable = false, length = 50)
    private String empName;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "id_number", nullable = false, length = 50)
    private String idNumber;

    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "tel", length = 50)
    private String tel;

    @Column(name = "photo_url")
    private String photoUrl;


    @Column(name="address")
    private String address;



    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;


    @Column(name = "hire_date")
    private LocalDate hireDate;
    @Column(name = "leave_date")
    private LocalDate leaveDate;
    @Column(name = "edit_date",insertable = false,updatable = false)
    private LocalDate editDate;

    @Column(name = "emp_state", nullable = false, length = 50)
    private String empState;

    @JsonManagedReference
    @OneToMany(mappedBy = "emp",cascade = CascadeType.ALL)
    private Set<Emergency> emergencies = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "emp",cascade = CascadeType.ALL)
    private Set<PermissionInfo> permissionInfos = new LinkedHashSet<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "emp",cascade = CascadeType.ALL)
    private Set<PermissionMove> permissionMoves = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "emp",cascade = CascadeType.ALL)
    private Set<ResignRecord> resignRecords = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "emp",cascade = CascadeType.ALL)
    private Set<TitleMove> titleMoves = new LinkedHashSet<>();

    public Employee() {
    }

    public Employee(String empName, LocalDate birth, String email, String idNumber, String gender, String phone, String tel, String photoUrl, String address,Title title,LocalDate hireDate, String empState) {
        this.empName = empName;
        this.birth = birth;
        this.email = email;
        this.idNumber = idNumber;
        this.gender = gender;
        this.phone = phone;
        this.tel = tel;
        this.photoUrl = photoUrl;
        this.address = address;
        this.title = title;
        this.hireDate = hireDate;
        this.empState = empState;
    }

    public Employee(String empName, String email, String phone, String tel, String address) {
        this.empName = empName;
        this.email = email;
        this.phone = phone;
        this.tel = tel;
        this.address = address;
    }

    public Employee(Integer empId, String empName, LocalDate birth, String email, String idNumber, String gender, String phone, String tel, String photoUrl, String address, Title title, String empState, LocalDate hireDate, LocalDate leaveDate, LocalDate editDate) {
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
        this.title = title;
        this.hireDate = hireDate;
        this.leaveDate = leaveDate;
        this.editDate = editDate;
        this.empState = empState;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id=" + empId +
                ", empName='" + empName + '\'' +
                ", birth=" + birth +
                ", email='" + email + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", tel='" + tel + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", address='" + address + '\'' +
                ", titleId=" +
                ", empState='" + empState + '\'' +
                '}';
    }
}