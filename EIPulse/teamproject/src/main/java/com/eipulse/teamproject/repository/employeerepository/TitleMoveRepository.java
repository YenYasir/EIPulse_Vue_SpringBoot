package com.eipulse.teamproject.repository.employeerepository;

import com.eipulse.teamproject.entity.employee.TitleMove;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TitleMoveRepository extends JpaRepository<TitleMove, Integer> {

    // 分頁功能
    @Query("FROM TitleMove move WHERE move.id >:id ORDER BY move.id")
    Page<TitleMove> findByMovePage(@Param("id")Integer id, Pageable pageable);
    // 模糊收尋-分頁
    @Query("FROM TitleMove move WHERE move.emp.empName LIKE %?1% ORDER BY move.id")
    Page<TitleMove> findByNamePage(@Param("name")String name,Pageable pageable);

//    @Query("SELECT move FROM TitleMove move JOIN move.emp emp WHERE emp.empName LIKE CONCAT('%', :name, '%') ORDER BY move.id")
//    Page<TitleMove> findByNamePage(@Param("name") String name, Pageable pageable);
}