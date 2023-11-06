package com.eipulse.teamproject.dto.employeedto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PermissionInfoDTO {

    private Integer id;
    private Integer empId;
    private String empName;
    private Integer permissionId;
    private String permissionName;
    private String beforePermissionName;
    private String afterPermissionName;
    private String reason;
    private LocalDate effectDate;
    private String approver;
    private LocalDateTime editDate;

    public PermissionInfoDTO() {
    }

    public PermissionInfoDTO(Integer id, Integer empId, Integer permissionId) {
        this.id = id;
        this.empId = empId;
        this.permissionId = permissionId;
    }

    public PermissionInfoDTO(Integer empId, Integer permissionId) {
        this.empId = empId;
        this.permissionId = permissionId;
    }

    public PermissionInfoDTO(Integer id, Integer empId, String empName, Integer permissionId, String permissionName) {
        this.id = id;
        this.empId = empId;
        this.empName = empName;
        this.permissionId = permissionId;
        this.permissionName = permissionName;
    }
}
