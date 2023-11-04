package com.eipulse.teamproject.repository.employeerepository;
import com.eipulse.teamproject.entity.employee.ResignRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResignRecordRepository extends JpaRepository<ResignRecord, Integer> {

    @Query("from ResignRecord resign where resign.emp.empId = ?1")
    ResignRecord findByEmpId (Integer empId);
}