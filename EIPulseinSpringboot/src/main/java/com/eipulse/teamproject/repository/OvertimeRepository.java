package com.eipulse.teamproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eipulse.teamproject.entitys.Overtime;

public interface OvertimeRepository extends JpaRepository<Overtime, Integer> {

}
