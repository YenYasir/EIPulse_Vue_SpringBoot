package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}