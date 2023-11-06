package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.entity.employee.PermissionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionInfoRepository extends JpaRepository<PermissionInfo, Integer> {
}