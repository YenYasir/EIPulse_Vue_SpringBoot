package com.eipulse.teamproject.entity.employee;

import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import com.eipulse.teamproject.entity.clocktimeentity.ClockTime;
import com.eipulse.teamproject.entity.salaryentity.EmpSalaryInfo;
import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import com.eipulse.teamproject.entity.salaryentity.SalaryHistory;
import com.eipulse.teamproject.entity.salaryentity.SalaryMonthRecord;
import com.eipulse.teamproject.entity.shoppingentity.Cart;
import com.eipulse.teamproject.entity.shoppingentity.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
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

    @Column(name="password")
    private String password;

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

    @JsonBackReference(value = "emp-title")
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

    @Column(name="emp_line_id")
    private String empLineId;

    @JsonManagedReference(value = "emp-emergencies")
    @OneToMany(mappedBy = "emp")
    private Set<Emergency> emergencies = new LinkedHashSet<>();

    @JsonManagedReference(value = "emp-permissionInfos")
    @OneToMany(mappedBy = "emp")
    private Set<PermissionInfo> permissionInfos = new LinkedHashSet<>();
    @JsonManagedReference(value = "emp-permissionMoves")
    @OneToMany(mappedBy = "emp")
    private Set<PermissionMove> permissionMoves = new LinkedHashSet<>();

    @JsonManagedReference(value = "emp-resignRecords")
    @OneToMany(mappedBy = "emp")
    private Set<ResignRecord> resignRecords = new LinkedHashSet<>();

    @JsonManagedReference(value = "emp-titleMoves")
    @OneToMany(mappedBy = "emp")
    private Set<TitleMove> titleMoves = new LinkedHashSet<>();


    @JsonManagedReference(value = "employee-clockTime")
    @OneToMany(mappedBy = "employee")
    private Set<ClockTime> clockTimes = new LinkedHashSet<>();


    @JsonManagedReference(value = "employee-attendance")
    @OneToMany(mappedBy = "employee")
    private Set<Attendance> attendances =new LinkedHashSet<>();


    @JsonManagedReference(value = "employee-orders")
    @OneToMany(mappedBy = "employee")
    private Set<Order> orders =new LinkedHashSet<>();


    @JsonManagedReference(value = "employee-cart")
    @OneToOne(mappedBy = "employee")
    private Cart cart;


    //salary
    @JsonManagedReference(value = "employee-salary-info")
    @OneToOne(fetch=FetchType.EAGER , mappedBy="employee",cascade =CascadeType.ALL)
    private EmpSalaryInfo empSalaryInfo;

    @JsonManagedReference(value = "employee-salary-detail")
    @OneToMany(fetch=FetchType.EAGER , mappedBy="employee",cascade =CascadeType.ALL)
    private List<SalaryDetail> salaryDetail;

    @JsonManagedReference(value = "employee-salary-month-records")
    @OneToMany(fetch=FetchType.EAGER , mappedBy="employee",cascade =CascadeType.ALL)
    private List<SalaryMonthRecord> salaryMonthRecords;

    @JsonManagedReference(value = "employee-salary-history")
    @OneToMany(fetch=FetchType.EAGER , mappedBy="employee",cascade =CascadeType.ALL)
    private List<SalaryHistory> salaryHistory;

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
}