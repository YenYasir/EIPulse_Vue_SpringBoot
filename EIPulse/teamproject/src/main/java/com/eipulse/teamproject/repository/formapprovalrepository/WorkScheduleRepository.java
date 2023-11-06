package com.eipulse.teamproject.repository.formapprovalrepository;
import com.eipulse.teamproject.entity.formapproval.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Integer> {

    @Query("from WorkSchedule where empId = :employeeID")
    List<WorkSchedule> findByEmployeeID(Integer employeeID);

    @Query("SELECT CASE WHEN COUNT(ws) > 0 THEN true ELSE false END FROM WorkSchedule ws WHERE ws.empId = :empId AND ws.weekday = :weekdays")
    boolean findRepetition (Integer empId,Integer weekdays);
}
