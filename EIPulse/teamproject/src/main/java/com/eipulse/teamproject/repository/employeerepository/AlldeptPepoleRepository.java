package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.entity.view.AlldeptPepole;
import com.eipulse.teamproject.entity.view.EmployeesName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlldeptPepoleRepository extends JpaRepository<AlldeptPepole, Integer> {

    @Query(nativeQuery = true,value = "select * from alldept_pepole where dept_id = :deptId order by title_name asc ")
    Page<AlldeptPepole> findAlldeptPepoleByDeptId(@Param("deptId") Integer deptId,Pageable pgb);
//    @Query(nativeQuery = true,value = "select * from alldept_pepole where dept_id = :deptId")
//    List<AlldeptPepole> findAlldeptPepoleByDeptId(@Param("deptId") Integer deptId);

}