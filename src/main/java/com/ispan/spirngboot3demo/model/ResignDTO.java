package com.ispan.spirngboot3demo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResignDTO {

    private Integer id;
    private Integer empId;
    private String approver;
    private String reason;
    private LocalDate editDate;
    public ResignDTO() {
    }

    public ResignDTO(Integer empId, String approver, String reason) {
        this.empId = empId;
        this.approver = approver;
        this.reason = reason;
    }

    public ResignDTO(Integer empId, String reason) {
        this.empId = empId;
        this.reason = reason;
    }

    public ResignDTO(Integer id, Integer empId, String reason, String approver) {
        this.id = id;
        this.empId = empId;
        this.approver = approver;
        this.reason = reason;
    }

    public ResignDTO(Integer id, Integer empId, String approver, String reason, LocalDate editDate) {
        this.id = id;
        this.empId = empId;
        this.approver = approver;
        this.reason = reason;
        this.editDate = editDate;
    }

    @Override
    public String toString() {
        return "ResignDTO{" +
                "id=" + id +
                ", empId=" + empId +
                ", approver='" + approver + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}

