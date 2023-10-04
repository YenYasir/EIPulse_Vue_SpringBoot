package com.eipulse.teamproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eipulse.teamproject.entitys.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {

	@Query(value = "from Employee where email = ?1")
	Optional<Login> findByEmail(String email);
}
