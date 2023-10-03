package com.eipulse.teamproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eipulse.teamproject.entitys.Emergency;

public interface DeptRepository extends JpaRepository<Emergency, Integer> {

}
