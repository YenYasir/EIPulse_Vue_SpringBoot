package com.eipulse.teamproject.entity.employee;

import com.eipulse.teamproject.entity.formapproval.FormRecord;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "resign_record", schema = "new_eipulse")
public class ResignRecord {

    @Id
    @Column(name = "resign_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference(value = "emp-resignRecords")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emp_id", nullable = false,unique = true)
    private Employee emp;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "leave_date",updatable = false)
    private LocalDate leaveDate;

    @Column(name = "approver", length = 50)
    private String approver;

    @Column(name = "edit_date",insertable = false,updatable = false)
    private LocalDate editDate;

    @Column(name = "quit")
    private boolean quit;

    @Column(name = "transfer_form")
    private boolean  transferForm;

    public ResignRecord() {
    }

    public ResignRecord(FormRecord form){
        this.emp = form.getEmployee();
        this.reason = form.getResignation().getReason();
        this.approver = form.getFormEventLog().get(0).getFormAuditEventLog().get(0).getEmployee().getEmpName();
        this.editDate = form.getEndDate().toLocalDate();
        this.quit = form.getResignation().isQuit();
        this.transferForm = form.getResignation().isTransferForm();
    }


    public ResignRecord(Integer id, Employee empId, String approver, String reason) {
        this.id = id;
        this.emp = emp;
        this.approver = approver;
        this.reason = reason;
    }

    public ResignRecord(Integer id, Employee emp, String reason, LocalDate leaveDate, String approver, LocalDate editDate) {
        this.id = id;
        this.emp = emp;
        this.reason = reason;
        this.leaveDate = leaveDate;
        this.approver = approver;
        this.editDate = editDate;
    }

    public ResignRecord(Employee emp, String approver, String reason) {
        this.emp = emp;
        this.approver = approver;
        this.reason = reason;
    }

    // add的建構子
    public ResignRecord(Employee emp, String reason, LocalDate leaveDate, boolean quit, boolean transferForm) {
        this.emp = emp;
        this.reason = reason;
        this.leaveDate = leaveDate;
        this.quit = quit;
        this.transferForm = transferForm;
    }

}