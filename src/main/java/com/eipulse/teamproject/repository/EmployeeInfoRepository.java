package com.eipulse.teamproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eipulse.teamproject.entitys.EmployeeInfo;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Integer> {

}
