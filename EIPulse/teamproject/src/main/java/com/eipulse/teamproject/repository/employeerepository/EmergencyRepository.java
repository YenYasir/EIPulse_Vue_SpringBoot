package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.dto.employeedto.EmergencyDTO;
import com.eipulse.teamproject.entity.employee.Emergency;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {
    @Query("from Emergency where emp.empId = ?1 order by emp.empId asc ")
    Page<EmergencyDTO> findEmergencyByEmpId (@Param("empId")Integer id, org.springframework.data.domain.Pageable pgb);

    @Query("from Emergency where emergencyId=?1")
    Emergency findEmergency(Integer emergencyId);
}