package com.eipulse.teamproject.service.eventservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eipulse.teamproject.dto.eventdto.NewsDTO;
import com.eipulse.teamproject.repository.eventrepository.NewsRepository;
import com.eipulse.teamproject.entity.evententity.Event;
import com.eipulse.teamproject.entity.evententity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@Transactional
public class NewsService {
	
	private NewsRepository newsRepo;

    @Autowired
    public NewsService(NewsRepository newsRepo) {
        this.newsRepo = newsRepo;
    }

//    新增消息
    public void saveNews(News news) {
    	newsRepo.save(news);
    }
    
    // 修改消息
    public void updateNews(Integer id, News news) {
    	newsRepo.updateNews(id, news); 
    }
    
//// 依照消息id刪除消息
//    public void deleteNewsById(Integer newsId) {
//        newsRepo.deleteById(newsId);
//    }
    
    // 標題模糊搜尋消息
//    PageRequest.of 方法中的 page 預設值是 0，表示第一頁，size為每頁要返回的筆數
    public Page<NewsDTO> searchByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.fingNewsByTitle(title, pageable);
    }

    // 依照時間順序顯示最新快訊
    public Page<NewsDTO> findAllNewsByPostTime(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.findAllNewsByPostTime(pageable);
    }
    
    // 顯示下架消息
    public Page<NewsDTO> findAllRemovedNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepo.findRemovedNews(pageable);
    }
    
    // 轉換傳回的 DTO 為 Entity存入資料庫
    public News convertDTOToEntity(NewsDTO newsDTO) {
        News newsEntity = new News();
        newsEntity.setNewsId(newsDTO.getNewsId());
        newsEntity.setTitle(newsDTO.getTitle());
        newsEntity.setContent(newsDTO.getContent());
        newsEntity.setFile(newsDTO.getFile());
        newsEntity.setPostTime(newsDTO.getPostTime());
        newsEntity.setVisible(newsDTO.getVisible());
        newsEntity.setPublisher(newsDTO.getPublisher());
        return newsEntity;
    }

}
