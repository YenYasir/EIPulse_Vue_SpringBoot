package com.ispan.spirngboot3demo.model;

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
}
