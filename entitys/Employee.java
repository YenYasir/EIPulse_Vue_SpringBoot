package com.eipulse.teamproject.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
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
    
    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName = "id")
    private Dept department;
    
    @Column(name="position")
    private String position;
    
    @Column(name = "gender")
    private Integer gender;
    
    @Column(name = "birth")
    private LocalDate birth;
    
    @Lob
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
 
    // 關聯員工狀態表
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "emp_stat",nullable = false)
    private EmpStatus empStatus;
    
    @Column(name = "hiredate")
    private LocalDate hiredate;
    
    @Column(name = "leave_date")
    private LocalDate leavedate;
    
    @Column(name="edit_date")
    private Timestamp updateTime;
    
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH},mappedBy="empId")
	private List<FormRecord> formRecord;
    

}

