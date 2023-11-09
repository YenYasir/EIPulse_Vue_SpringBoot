package com.eipulse.teamproject.dto.employeedto;

import com.eipulse.teamproject.entity.employee.Emergency;
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
    private String empName;

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

    public EmergencyDTO(Emergency emergency) {
        this.emergencyId = emergency.getEmergencyId();
        this.empId = emergency.getEmp().getEmpId();
        this.empName = emergency.getEmp().getEmpName();
        this.emergencyName = emergency.getEmergencyName();
        this.phone = emergency.getPhone();
        this.relation = emergency.getRelation();
    }
}
