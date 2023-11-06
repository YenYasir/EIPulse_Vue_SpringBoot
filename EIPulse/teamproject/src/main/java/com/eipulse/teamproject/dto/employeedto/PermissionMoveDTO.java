package com.eipulse.teamproject.dto.employeedto;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PermissionMoveDTO {
    private Integer id;
    private Integer empId;
    private String beforePermissionName;
    private String afterPermissionName;
    private String reason;
    private LocalDate effectDate;
    private String approver;
    private LocalDateTime editDate;

    public PermissionMoveDTO() {
    }

    public PermissionMoveDTO(Integer id, Integer empId, String beforePermissionName, String afterPermissionName, String reason, LocalDate effectDate, String approver, LocalDateTime editDate) {
        this.id = id;
        this.empId = empId;
        this.beforePermissionName = beforePermissionName;
        this.afterPermissionName = afterPermissionName;
        this.reason = reason;
        this.effectDate = effectDate;
        this.approver = approver;
        this.editDate = editDate;
    }
}
