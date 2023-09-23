package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.spirngboot3demo.model.Title;

public interface TitleRepository extends JpaRepository<Title,Integer> {

}
