package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.Emergency;

public interface DeptRepository extends JpaRepository<Emergency, Integer> {

}
