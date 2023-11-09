package com.eipulse.teamproject.repository.eventrepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.teamproject.entity.evententity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	
	// 依照活動標題執行模糊搜尋
	@Query("SELECT e FROM Event e WHERE e.title LIKE %:keyword%")
    List<Event> findEventsByKeyword(@Param("keyword") String keyword);
	
	// 依照開始時間讀取自身的所有活動
	@Query("SELECT e FROM Event e WHERE e.userId = :userId ORDER BY e.start") 
	List<Event> findByUserId(Integer userId);
	
//	// 依照開始時間讀取所有活動
	@Query("SELECT e FROM Event e ORDER BY e.start") 
	List<Event> findAllByStartDate();
	
	// 顯示當前時間後的所有活動
	@Query("SELECT e FROM Event e WHERE e.start >= :today ORDER BY e.start")
	List<Event> findAllEventsAfterToday(@Param("today") LocalDateTime today);

	// 根據id刪除活動
	@Transactional
	@Modifying
	@Query("DELETE FROM Event e WHERE e.eventId = :eventId")
	void deleteById(@Param("eventId") Integer eventId);
	
	// 透過標題找出活動
	@Query("SELECT e FROM Event e WHERE e.eventId = :eventId")
	 Optional<Event> findById(Integer eventId);
	
//	// 依照活動ID修改活動
//	@Transactional
//    @Modifying
//    @Query("UPDATE Event e SET e.title = :title, e.start = :start, e.end = :end, e.description = :description WHERE e.eventId = :eventId")
//    void updateEventDetails(@Param("eventId") Integer eventId, @Param("title") String title, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("description") String description);

	// 依照活動ID修改活動
	@Modifying 
	@Query("UPDATE Event e SET e.title = :#{#event.title}, e.start = :#{#event.start}, e.end = :#{#event.end}, e.description = :#{#event.description} WHERE e.eventId = :id")
	void updateEvent(@Param("id") Integer id, @Param("event") Event event);
}
