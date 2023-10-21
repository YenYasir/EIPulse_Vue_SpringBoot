package com.eipulse.teamproject.entity;

import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import com.eipulse.teamproject.entity.clocktimeentity.ClockTime;
import com.eipulse.teamproject.entity.shoppingentity.EmployeeGiftCash;
import com.eipulse.teamproject.entity.shoppingentity.Order;
import com.eipulse.teamproject.entity.shoppingentity.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private Set<EmployeeGiftCash> employeeGiftCashSet;

    @OneToOne(mappedBy = "employee")
    private ShoppingCart shoppingCarts;

    @JsonManagedReference
    @OneToMany(mappedBy = "employee")
    private Set<Order> orders;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ClockTime> clockTime;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    Set<Attendance> attendance;


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
                ", employeeGiftCashSet=" + employeeGiftCashSet +
                ", shoppingCarts=" + shoppingCarts +
                ", orders=" + orders +
                ", clockTime=" + clockTime +
                ", attendance=" + attendance +
                '}';
    }
}

