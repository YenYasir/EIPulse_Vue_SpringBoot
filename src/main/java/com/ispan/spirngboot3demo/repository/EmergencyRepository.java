package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.spirngboot3demo.model.Dept;

public interface EmergencyRepository extends JpaRepository<Dept, Integer> {

}
