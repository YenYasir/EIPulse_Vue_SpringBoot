package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.TitleMove;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleMoveRepository extends JpaRepository<TitleMove, Integer> {
}