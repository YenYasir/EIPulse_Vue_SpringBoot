package com.eipulse.teamproject.repository.clocktimerepository;

import com.eipulse.teamproject.entity.clocktimeentity.ClockTime;
import com.eipulse.teamproject.entity.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ClockTimeRepository extends JpaRepository<ClockTime, Integer> {


//  抓取員工最後一筆打卡紀錄用於顯示在主頁
    @Query("from ClockTime c where c.employee.empId = :empId order by c.time DESC ")
    List<ClockTime> findByEmpIdLastTime(Integer empId);

//    抓取員工當天所有打卡記錄
//    可以改用dto取
    @Query("from ClockTime c where c.employee = :employee and c.time between :startTime and :endTime order by c.time asc ")
    List<ClockTime> findByTimeAndEmployee(Employee employee, LocalDateTime startTime, LocalDateTime endTime);


    @Query("from ClockTime c where c.employee.empId = :empId and DATE(c.time) between :startDate and :endDate order by c.time asc ")
    Page<ClockTime> findByTimeBetweenAndEmployee(@Param("empId") Integer empId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,Pageable pageable);

    @Query("from ClockTime c where DATE(c.time) between :startDate and :endDate order by c.time asc ")
    Page<ClockTime> findClockTimesByDate(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate,Pageable pageable);
}