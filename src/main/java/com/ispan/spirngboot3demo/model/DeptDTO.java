package com.ispan.spirngboot3demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptDTO {
    private Integer deptId;
    private String deptName;
    private String deptOffice;

    public DeptDTO() {
    }

    public DeptDTO(Integer deptId, String deptName, String deptOffice) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptOffice = deptOffice;
    }

    public DeptDTO(String deptName, String deptOffice) {
        this.deptName = deptName;
        this.deptOffice = deptOffice;
    }
}
