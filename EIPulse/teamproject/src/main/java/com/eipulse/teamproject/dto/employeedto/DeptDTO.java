package com.eipulse.teamproject.dto.employeedto;

import com.eipulse.teamproject.entity.employee.Dept;
import com.eipulse.teamproject.entity.employee.Title;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DeptDTO {
    private Integer deptId;
    private String deptName;
    private String deptOffice;
    private List<TitleDTO> titleDTOS;


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
    public DeptDTO(Dept dept) {
        this.deptId = dept.getDeptId();
        this.deptName = dept.getDeptName();
        this.deptOffice = dept.getDeptOffice();
        this.titleDTOS = new ArrayList<>();

        for (Title a : dept.getTitles()) {
            TitleDTO titleDTO1 = new TitleDTO(a);
            titleDTOS.add(titleDTO1);
        }

    }
}
