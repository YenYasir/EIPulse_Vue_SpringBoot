package com.ispan.spirngboot3demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyDTO {

    private Integer emergencyId;
    private String emergencyName;
    private String phone;
    private String relation;
    private Integer empId;

    public EmergencyDTO() {
    }

    public EmergencyDTO(Integer emergencyId, String emergencyName, String phone, String relation, Integer empId) {
        this.emergencyId = emergencyId;
        this.emergencyName = emergencyName;
        this.phone = phone;
        this.relation = relation;
        this.empId = empId;
    }

    public EmergencyDTO(Integer emergencyId, String emergencyName, String phone, String relation) {
        this.emergencyId = emergencyId;
        this.emergencyName = emergencyName;
        this.phone = phone;
        this.relation = relation;
    }

    public EmergencyDTO(String emergencyName, String phone, String relation, Integer empId) {
        this.emergencyName = emergencyName;
        this.phone = phone;
        this.relation = relation;
        this.empId = empId;
    }
}
