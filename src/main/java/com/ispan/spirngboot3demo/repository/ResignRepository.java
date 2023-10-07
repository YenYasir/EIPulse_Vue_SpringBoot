package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.ResignRecord;
import com.ispan.spirngboot3demo.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResignRepository extends JpaRepository<ResignRecord,Integer> {

}
