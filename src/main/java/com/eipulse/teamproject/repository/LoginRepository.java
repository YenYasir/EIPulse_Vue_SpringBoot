package com.eipulse.teamproject.repository;

import com.eipulse.teamproject.entitys.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginRepository extends JpaRepository<Login,Integer> {


    @Query(value = "from Login  l where l.employee.email = ?1")
    Login findByEmail(String email);
}
