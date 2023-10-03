<<<<<<< HEAD
package com.eipulse.teamproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eipulse.teamproject.entitys.ClockTime;

public interface ClockTimeRepository extends JpaRepository<ClockTime, Integer> {

//    自定義查詢方法 JPQL=HQl, HQL!=JPQL
	@Query("FROM ClockTime c WHERE c.empId IN :empId")
	List<ClockTime> findAllByIdTime(@Param("empId") Integer empId);

}
=======
//package com.eipulse.teamproject.repository;
//
//import com.eipulse.teamproject.entitys.ClockTime;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//
//public interface ClockTimeRepository extends JpaRepository<ClockTime, Integer> {
//
////    自定義查詢方法 JPQL=HQl, HQL!=JPQL
//    @Query("FROM ClockTime c WHERE c.empId IN :empId")
//    List<ClockTime> findAllByIdTime(@Param("empId") Integer empId);
//
//}
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
