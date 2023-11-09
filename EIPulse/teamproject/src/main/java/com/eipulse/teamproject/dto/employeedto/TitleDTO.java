package com.eipulse.teamproject.dto.employeedto;

import com.eipulse.teamproject.entity.employee.Title;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TitleDTO {

    private Integer id;
    private String titleName;
    private Integer deptId;

    public TitleDTO() {
    }

    public TitleDTO(String titleName, Integer deptId) {
        this.titleName = titleName;
        this.deptId = deptId;
    }
    public TitleDTO(Integer id, String titleName, Integer deptId) {
        this.id = id;
        this.titleName = titleName;
        this.deptId = deptId;
    }
    public TitleDTO(Title title) {
        this.titleName = title.getTitleName();
        this.id = title.getId();
    }
}

