package com.eipulse.teamproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eipulse.teamproject.entitys.Dept;

public interface EmergencyRepository extends JpaRepository<Dept, Integer> {

}
