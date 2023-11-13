package com.eipulse.teamproject.dto.eventdto;

import com.eipulse.teamproject.entity.evententity.News;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

// DTO的目的是將Entity轉換為前端需要的格式

@Getter
@Setter
public class NewsDTO {
	
	 	private Integer newsId;
	    private String title;
	    private String content;
	    private String file;
	    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	    private LocalDateTime postTime;
//	    private Date removeDate;
	    private Integer publisher;
	    private String publisherName;  
	    private String publisherTitle;
	    private Boolean visible=true;

	    
	    // 用於前端畫面顯示
	    public NewsDTO(News news) {
	        this.newsId = news.getNewsId();
	        this.title = news.getTitle();
	        this.content = news.getContent();
	        this.file = news.getFile();
	        this.postTime = news.getPostTime();
	        this.visible=news.getVisible();
//	        this.removeDate=news.getRemoveDate();
	        this.publisher=news.getPublisher();
	        this.publisherName=news.getEmp().getEmpName();
	        this.publisherTitle=news.getEmp().getTitle().getTitleName();
	    }
	    
//	 // 用於前端回傳資料
//	    public NewsDTO(Integer newsId, Integer publisher, String title, String content, String file, LocalDateTime postTime, Date removeDate) {
//	        this.newsId = newsId;
//	        this.publisher = publisher;
//	        this.title = title;
//	        this.content = content;
//	        this.file = file;
//	        this.postTime = postTime;
//	        this.removeDate = removeDate;	        
//	    }
	    
	    public NewsDTO() {
	        
	    }

}
