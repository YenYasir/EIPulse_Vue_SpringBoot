package com.eipulse.teamproject.repository.salaryrepository;
import com.eipulse.teamproject.entity.salaryentity.SalaryMonthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SalaryMonthRecordRepository extends JpaRepository<SalaryMonthRecord, Integer> {
	
	// 將紀錄表、明細 status欄位改成已計薪(1)
	@Transactional
	@Modifying
	@Query(value ="update salary_month_record r join salary_detail d on r.id = d.record_id  set r.status =1 ,d.status= 1 where r.emp_id = :empId and r.id = :id ;" ,nativeQuery = true)
//	@Query(value ="update salary_month_record r join salary_detail d on r.id = d.record_id  set r.status = :status1 ,d.status= :status2 where r.emp_id = :empId and r.id = :id ;" ,nativeQuery = true)
	 Integer translateStatusUnabled(@Param(value="id") Integer id,@Param(value="empId") Integer empId);
	
	// 員工編號找歷史月紀錄/明細 for 薪資明細
	@Query(value = "select * from salary_month_record where emp_id = :empId and sl_Year = :year and sl_Month = :month", nativeQuery = true)
	SalaryMonthRecord findByEmpId(@Param(value="empId") Integer empId,@Param(value="year") Integer year,@Param(value="month")Integer month);
	
	//透年月找
	@Query(value = "select * from salary_month_record where sl_Year = :year and sl_Month = :month", nativeQuery = true)
	 List<SalaryMonthRecord> findBySlYearAndSlMonth(@Param(value="year") Integer year,@Param(value="month")Integer month);
}