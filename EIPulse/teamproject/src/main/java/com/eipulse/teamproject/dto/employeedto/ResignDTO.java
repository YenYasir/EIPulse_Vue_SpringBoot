package com.eipulse.teamproject.dto.employeedto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ResignDTO {

    private Integer id;
    private Integer empId;
    private String reason;
    private LocalDate leaveDate;
    private String approver;
    private LocalDate editDate;
    private boolean quit;
    private boolean  transferForm;
    public ResignDTO() {
    }

    public ResignDTO(Integer empId, String approver, String reason) {
        this.empId = empId;
        this.approver = approver;
        this.reason = reason;
    }

    // add的建構子
    public ResignDTO(Integer empId, String reason, LocalDate leaveDate, boolean quit, boolean transferForm) {
        this.empId = empId;
        this.reason = reason;
        this.leaveDate = leaveDate;
        this.quit = quit;
        this.transferForm = transferForm;
    }


    // 查詢單筆
    public ResignDTO(Integer id, Integer empId, String reason, LocalDate leaveDate, String approver, boolean quit, boolean transferForm) {
        this.id = id;
        this.empId = empId;
        this.reason = reason;
        this.leaveDate = leaveDate;
        this.approver = approver;
        this.quit = quit;
        this.transferForm = transferForm;
    }


    // 查詢全部
    public ResignDTO(Integer id, Integer empId, String reason, LocalDate leaveDate, String approver, LocalDate editDate, boolean quit, boolean transferForm) {
        this.id = id;
        this.empId = empId;
        this.reason = reason;
        this.leaveDate = leaveDate;
        this.approver = approver;
        this.editDate = editDate;
        this.quit = quit;
        this.transferForm = transferForm;
    }
}

