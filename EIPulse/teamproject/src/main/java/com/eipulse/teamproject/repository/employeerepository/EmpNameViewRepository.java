package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.entity.employee.EmpNameView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpNameViewRepository extends JpaRepository<EmpNameView, Integer> {

    @Query("SELECT v FROM EmpNameView v")
    List<EmpNameView> findAllEmployeeNames();
}
