package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.DeptMoveRecord;
import com.ispan.spirngboot3demo.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptMoveRepository extends JpaRepository<DeptMoveRecord,Integer> {

}
