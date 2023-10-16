package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
}