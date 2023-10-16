package com.ispan.spirngboot3demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionInfoDTO {

    private Integer id;
    private Integer empId;
    private String empName;
    private Integer permissionId;
    private String permissionName;

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

    @Override
    public String toString() {
        return "PermissionInfoDTO{" +
                "id=" + id +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
