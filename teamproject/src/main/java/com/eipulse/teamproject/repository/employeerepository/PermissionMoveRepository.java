package com.eipulse.teamproject.repository.employeerepository;
import com.eipulse.teamproject.entity.employee.PermissionMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionMoveRepository extends JpaRepository<PermissionMove, Integer> {
    @Query("from PermissionMove move where move.emp.empId = ?1")
    PermissionMove findByEmpId(Integer empId);
}