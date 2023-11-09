package com.eipulse.teamproject.repository.employeerepository;


import com.eipulse.teamproject.dto.employeedto.TitleDTO;
import com.eipulse.teamproject.entity.employee.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Integer> {

    @Query("select new com.eipulse.teamproject.dto.employeedto.TitleDTO(t.id,t.titleName) from Title t")
    List<TitleDTO> findAllTitle();
}