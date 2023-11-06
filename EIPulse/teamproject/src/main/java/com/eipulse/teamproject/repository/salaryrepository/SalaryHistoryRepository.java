package com.eipulse.teamproject.repository.salaryrepository;
import com.eipulse.teamproject.entity.salaryentity.SalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, Integer> {
	
	//依員工編號搜尋並依時間排序(最新)
	@Query(value="from SalaryHistory where empId= :empId order by adjustedDate desc")
	 List<SalaryHistory> findByEmpId(@Param(value="empId") Integer empId);
}
