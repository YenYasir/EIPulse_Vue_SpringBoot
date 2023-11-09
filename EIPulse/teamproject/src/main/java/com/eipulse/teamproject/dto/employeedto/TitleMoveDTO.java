package com.eipulse.teamproject.dto.employeedto;

import com.eipulse.teamproject.entity.employee.TitleMove;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TitleMoveDTO {
    private Integer id;
    private Integer empId;
    private String beforeDeptInfo;
    private String afterDeptInfo;
    private String reason;
    private LocalDate effectDate;
    private String approver;
    private LocalDateTime editDate;
    private Integer titleId;
    private String empName;


    public TitleMoveDTO(Integer id, Integer empId, String beforeDeptInfo, String afterDeptInfo, String reason, LocalDate effectDate, String approver, LocalDateTime editDate) {
        this.id = id;
        this.empId = empId;
        this.beforeDeptInfo = beforeDeptInfo;
        this.afterDeptInfo = afterDeptInfo;
        this.reason = reason;
        this.effectDate = effectDate;
        this.approver = approver;
        this.editDate = editDate;
    }

    public TitleMoveDTO(TitleMove move){
        this.id = move.getId();
        this.empId = move.getEmp().getEmpId();
        this.empName = move.getEmp().getEmpName();
        this.beforeDeptInfo = move.getBeforeDeptInfo();
        this.afterDeptInfo = move.getAfterDeptInfo();
        this.reason = move.getReason();
        this.effectDate = move.getEffectDate();
        this.approver = move.getApprover();
        this.editDate = move.getEditDate();
    }


    public TitleMoveDTO(Integer id) {
    }
}
