package com.eipulse.teamproject.repository.clocktimerepository;

import com.eipulse.teamproject.entity.clocktimeentity.Attendance;
import com.eipulse.teamproject.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

//    顯示員工選擇日期的當日出勤時數狀況
    Optional<Attendance> findByDateAndEmployee(LocalDate date, Employee employee);


    //依區間抓取員工出勤狀況，最大為單月
    @Query("from Attendance  a where a.date between :startDate and :endDate and a.employee.empId = :empId order by a.date ASC")
    public Page<Attendance> findByDateBetweenEmpAttendance(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate , @Param("empId") Integer empId, Pageable pageable);

    //依區間抓取全員工出勤狀況，最大為單月，依照時間舊到新排序
    @Query("from Attendance  a where a.date between :startDate and :endDate order by a.date ASC ")
    public Page<Attendance> findByDateBetweenAllEmpAttendance(@Param("startDate") LocalDate startDate,@Param("endDate")LocalDate endDate,Pageable pageable);


}