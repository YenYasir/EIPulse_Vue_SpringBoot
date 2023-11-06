package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.entity.employee.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}