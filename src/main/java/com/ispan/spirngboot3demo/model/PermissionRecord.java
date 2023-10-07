package com.ispan.spirngboot3demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="permission_move")
public class PermissionRecord {

     @Id
     @Column(name = "id")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private  Integer id;

     @Column(name = "reason")
     private  String reason;

     @Column(name = "before_role_id")
     private Integer beforeRoleId;

     @Column(name = "after_role_id")
     private  Integer afterRoleId;

     @Column(name = "move_date")
     private  LocalDate moveDate;

     @Column(name = "approver")
     private String approver;

     @Column(name="edit_date")
     private LocalDate editDate;


     @OneToOne(fetch = FetchType.LAZY)
//      @JoinColumn(name = "emp_id", referencedColumnName = "empId", insertable = false, updatable = false)
     @JoinColumn(name = "empId", referencedColumnName = "emp_id")//, insertable = false, updatable = false)
     private Employee employee;

}
