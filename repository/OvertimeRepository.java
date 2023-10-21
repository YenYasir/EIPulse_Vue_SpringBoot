package com.eipulse.teamproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.eipulse.teamproject.entitys.Overtime;

@Repository
public interface OvertimeRepository extends JpaRepository<Overtime, Integer> {
	
	// 透過表單編號查詢
	@Query("SELECT o FROM Overtime o WHERE o.formId = :formId")
	Overtime findByFormId(@Param("formId") Integer formId);

	// 透過請假類型查詢
	@Query("SELECT o FROM Overtime o WHERE o.type.id = :typeId")
	List<Overtime> findByOvertimeType(@Param("typeId") Integer typeId);
	
	// 透過表單編號刪除
	@Modifying
	@Query("DELETE FROM Overtime o WHERE o.formId = :formId")
	void deleteByFormId(@Param("formId") Integer formId);

}
