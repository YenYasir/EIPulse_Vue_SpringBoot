package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.PermissionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionInfoRepository extends JpaRepository<PermissionInfo, Integer> {
}