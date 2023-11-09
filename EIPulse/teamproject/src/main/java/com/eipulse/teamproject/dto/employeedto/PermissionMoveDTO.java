package com.eipulse.teamproject.dto.employeedto;


import com.eipulse.teamproject.entity.employee.PermissionMove;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PermissionMoveDTO {
    private Integer id;
    private Integer empId;
    private String empName;
    private String beforePermissionName;
    private String afterPermissionName;
    private String reason;
    private LocalDate effectDate;
    private String approver;
    private LocalDateTime editDate;

    public PermissionMoveDTO() {
    }

    public PermissionMoveDTO(PermissionMove move){
        this.id = move.getId();
        this.empId = move.getEmp().getEmpId();
        this.empName = move.getEmp().getEmpName();
        this.beforePermissionName = move.getBeforePermissionName();
        this.afterPermissionName = move.getAfterPermissionName();
        this.reason = move.getReason();
        this.effectDate = move.getEffectDate();
        this.approver = move.getApprover();
        this.editDate = move.getEditDate();
    }
}
