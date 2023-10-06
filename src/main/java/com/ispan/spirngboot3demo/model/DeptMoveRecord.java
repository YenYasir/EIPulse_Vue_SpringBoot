package com.ispan.spirngboot3demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="dept_move")
public class DeptMoveRecord {

	 @Id
     @Column(name = "id")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private  Integer id;

     @Column(name = "reason")
     private  String reason;
     
     @Column(name = "after_dept_id")
     private  Integer afterDeptId;
     
     @Column(name = "move_date")
     private  LocalDate moveDate;
     
     @OneToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "emp_id", referencedColumnName = "empId", insertable = false, updatable = false)
     @JoinColumn(name = "empId", referencedColumnName = "emp_id", insertable = false, updatable = false)
     private Employee employee;
}
