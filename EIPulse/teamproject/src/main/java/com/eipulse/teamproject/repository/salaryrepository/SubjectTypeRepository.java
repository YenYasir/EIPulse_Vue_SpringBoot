package com.eipulse.teamproject.repository.salaryrepository;

import com.eipulse.teamproject.entity.salaryentity.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SubjectTypeRepository extends JpaRepository<SubjectType, Integer> {

	//依名字模糊搜尋
	 List<SubjectType> findBySubjectNameLike(String name);
	
	
	// 搜尋狀態為啟用的科目
	@Query( value = "select * from subject_type where status = 1", nativeQuery = true)
	 List<SubjectType>findByStatus();
	
	// 搜尋計算類型為"加 (P)" & 啟用的科目
	@Query( value = "select * from subject_type where status = '1' and calculate_type='P'" , nativeQuery = true)
	 List<SubjectType>findTypeIsP();
	
	// 搜尋計算類型為"減 (M)"科目啟用的科目
	@Query( value = "select * from subject_type where status = '1' and calculate_type='M'" , nativeQuery = true)
	 List<SubjectType>findTypeIsM();
	
	
	// 更新科目狀態
	@Modifying
	@Query( value = "update subject_type SET status = :status where subject_id =:id" , nativeQuery = true)
	Integer transStatus(@Param("status")String status,@Param("id") Integer id);

}
