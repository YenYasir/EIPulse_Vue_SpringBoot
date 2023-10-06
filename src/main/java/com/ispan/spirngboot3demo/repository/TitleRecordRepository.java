package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.Title;
import com.ispan.spirngboot3demo.model.TitleMoveRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRecordRepository extends JpaRepository<TitleMoveRecord,Integer> {

}
