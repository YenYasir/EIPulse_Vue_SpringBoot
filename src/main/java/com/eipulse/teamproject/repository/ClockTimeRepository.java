package com.eipulse.teamproject.repository;

import com.eipulse.teamproject.entitys.ClockTime;
import com.eipulse.teamproject.entitys.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ClockTimeRepository extends JpaRepository<ClockTime, Integer> {

//    自定義查詢方法 JPQL=HQl, HQL!=JPQL。顯示員工所有打卡記錄
    @Query("FROM ClockTime c WHERE c.employee.empId IN :empId")
    List<ClockTime> findAllByIdTime(@Param("empId") Integer empId);

//  抓取員工最後一筆打卡紀錄用於顯示在主頁
    @Query("from ClockTime c where c.employee.empId = :empId order by c.time DESC ")
    List<ClockTime> findByEmpIdLastTime(Integer empId);

//    抓取員工當天所有打卡記錄
//    可以改用dto取
    @Query("from ClockTime c where c.employee = :employee and c.time between :startTime and :endTime")
    List<ClockTime> findByTimeAndEmployee(Employee employee, LocalDateTime startTime, LocalDateTime endTime);

}