package com.ispan.spirngboot3demo.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

@Getter
@Setter
public class PermissionMoveDTO {
    private Integer id;
    private Integer empId;
    private String beforePermissionName;
    private String afterPermissionName;
    private String reason;
    private String approver;

    public PermissionMoveDTO() {
    }

    public PermissionMoveDTO(Integer id, Integer empId, String beforePermissionName, String afterPermissionName, String reason, String approver) {
        this.id = id;
        this.empId = empId;
        this.beforePermissionName = beforePermissionName;
        this.afterPermissionName = afterPermissionName;
        this.reason = reason;
        this.approver = approver;
    }
}
