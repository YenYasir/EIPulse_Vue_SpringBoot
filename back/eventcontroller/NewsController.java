package com.eipulse.teamproject.controller.eventcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.dto.eventdto.NewsDTO;
import com.eipulse.teamproject.entity.evententity.News;
import com.eipulse.teamproject.service.eventservice.NewsService;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/news")
public class NewsController {
	
	 private  NewsService newsService;

	    @Autowired
	    public NewsController(NewsService newsService) {
	        this.newsService = newsService;
	    }
	    
	    // 新增
	    @PostMapping("/")
	    public ResponseEntity<?> createNews(@RequestBody News news) {
	         newsService.saveNews(news);
	         return new ResponseEntity<>(HttpStatus.OK);
	    }

	    // 根據消息編號修改內容
	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateNews(@PathVariable("id") Integer id, @RequestBody NewsDTO newsDTO) {
	    	 News newsEntity = newsService.convertDTOToEntity(newsDTO);
	    	 newsService.updateNews(id,newsEntity);
	         return new ResponseEntity<>(HttpStatus.OK);
	    }

	    // 依照id刪除消息
//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<?> deleteNews(@PathVariable("id") Integer id) {
//	        newsService.deleteNewsById(id);
//	        return new ResponseEntity<>(HttpStatus.OK);
//	    }

	    // 根據消息標題抓取資料，預設從第一頁開始，一次回傳十筆
	    @GetMapping("/search")
	    public Page<NewsDTO> searchNewsByTitle(
	            @RequestParam("title") String title,
	            @RequestParam(name = "page", defaultValue = "0") int page,
	            @RequestParam(name = "size", defaultValue = "8") int size) {
	        return newsService.searchByTitle(title, page-1, size);
	    }

	    // 依照時間順序得到所有消息
	    @GetMapping("/")
	    public Page<NewsDTO> getAllNewsByPostTime(
	            @RequestParam(name = "page", defaultValue = "0") int page,
	            @RequestParam(name = "size", defaultValue = "8") int size) {
	    	return newsService.findAllNewsByPostTime(page-1, size);
	    }
	    
	    // 取得下架消息
	    @GetMapping("/remove")
	    public Page<NewsDTO> getRemovedAllNews(
	            @RequestParam(name = "page", defaultValue = "0") int page,
	            @RequestParam(name = "size", defaultValue = "8") int size) {
	        return newsService.findAllRemovedNews(page-1, size);
	    }
	    



}
