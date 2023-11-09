package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.entity.employee.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {
}