package com.ispan.spirngboot3demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.spirngboot3demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
