package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {
}