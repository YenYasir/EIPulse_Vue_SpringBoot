package com.ispan.spirngboot3demo.repository;

import com.ispan.spirngboot3demo.model.PermissionMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionMoveRepository extends JpaRepository<PermissionMove, Integer> {
    @Query("from PermissionMove move where move.emp.empId = ?1")
    PermissionMove findByEmpId(Integer empId);
}