package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.ResignRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResignRecordRepository extends JpaRepository<ResignRecord, Integer> {
}