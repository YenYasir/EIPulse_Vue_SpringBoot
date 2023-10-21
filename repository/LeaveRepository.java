package com.eipulse.teamproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.eipulse.teamproject.entitys.Leave;

// Repository僅涉及到對數據庫的基本操作
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {
	
	// 透過表單編號查詢
	@Query("SELECT l FROM Leave l WHERE l.formId = :formId")
	Leave findByFormId(@Param("formId") Integer formId);

	// 透過請假類型查詢
	@Query("SELECT l FROM Leave l WHERE l.leaveType.typeId = :typeId")
	List<Leave> findLeaveByTypeId(@Param("typeId") Integer typeId);

//	@Query("SELECT l FROM Leave l WHERE l.leaveType = :leaveType")
//	List<Leave> findByLeaveType(@Param("leaveType") Integer leaveType);
	
	// 透過表單編號刪除
	@Modifying
	@Query("DELETE FROM Leave l WHERE l.formId = :formId")
	void deleteByFormId(@Param("formId") Integer formId);
	
	// 透過表單編號修改
//    @Modifying
//    @Query("UPDATE Leave l SET l.leaveType.typeId = :typeId, l.reason = :reason, l.days = :days, l.hours = :hours, l.startTime = :startTime, l.file = :file WHERE l.formId = :formId")
//    Leave updateLeaveForm(@Param("formId") Integer formId, @Param("typeId") Integer typeId, @Param("reason") String reason, @Param("days") Integer days, @Param("hours") Integer hours, @Param("startTime") LocalDateTime startTime, @Param("file") byte[] file);




}
