package com.eipulse.teamproject.repository.eventrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eipulse.teamproject.dto.eventdto.NewsDTO;
import com.eipulse.teamproject.entity.evententity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
	
	// 根據標題模糊搜尋消息
	@Query("SELECT n FROM News n WHERE n.visible = true AND n.title LIKE CONCAT('%', :title, '%') ORDER BY n.postTime DESC")
	Page<NewsDTO> fingNewsByTitle(@Param("title") String title, Pageable pageable);

	// 根據時間來顯示最新快訊
	 @Query("SELECT n FROM News n WHERE n.visible = true ORDER BY n.postTime DESC ")
	 Page<NewsDTO> findAllNewsByPostTime(Pageable pageable);
	 
    // 顯示下架的消息，給管理端執行
	 @Query("SELECT n FROM News n WHERE n.visible = false and n.publisher = publisher ORDER BY n.postTime DESC ")
	 Page<NewsDTO> findRemovedNews(@Param("publisher") Integer publisher,Pageable pageable);
	 
	// 依照消息id修改消息
	@Modifying
	@Query("UPDATE News n SET n.title = :#{#news.title}, n.content = :#{#news.content}, n.file = :#{#news.file}, n.visible = :#{#news.visible}  WHERE n.newsId = :id")
	void updateNews(@Param("id") Integer id,@Param("news") News news);


}