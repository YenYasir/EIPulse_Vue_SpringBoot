package com.eipulse.teamproject.repository;

import com.eipulse.teamproject.entitys.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login,Integer> {


    @Query(value = "from Login  l where l.employee.email= ?1")
    Optional<Login> findByEmail(String email);
}
