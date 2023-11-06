package com.eipulse.teamproject.repository.salaryrepository;
import com.eipulse.teamproject.entity.salaryentity.SalaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SalaryDetailRepository extends JpaRepository<SalaryDetail, Integer> {
	
	// 用 slyear slmonth slempId slsubjectId 查詢是否當月已建立該科目明細
	@Query( value = "select count(*) from salary_detail where sl_year = :year and sl_month = :month and emp_id = :empId and subject_id = :subjectId", nativeQuery = true)
	 Integer findDetailIsExit(@Param(value = "year") Integer year, @Param(value = "month") Integer month, @Param(value = "empId") Integer empId,@Param(value = "subjectId") Integer subjectId);

	@Query(value = "select * from salary_detail where record_id=:id and emp_id = :empId" , nativeQuery = true)
	 List<SalaryDetail> findDetailByRecordId (@Param(value="id")Integer recordId, @Param(value="empId")Integer empId);
}
 