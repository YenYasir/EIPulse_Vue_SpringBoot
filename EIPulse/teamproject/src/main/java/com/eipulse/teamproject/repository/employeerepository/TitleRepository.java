package com.eipulse.teamproject.repository.employeerepository;


import com.eipulse.teamproject.entity.employee.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Integer> {
}