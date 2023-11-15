package com.eipulse.teamproject.entity.evententity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name="news")
public class News {
	
	  @Id
	  @Column(name="news_id")
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer newsId;
	  
	  @Column(name="title")
	  private String title;
	  
	  @Column(name="content")
	  private String content;
	  
	  @Column(name="file")
	  private String file;
	  
	  @CreationTimestamp
	  @Column(name="post_time")
	  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	  private LocalDateTime postTime;
	  
	  @Column(name = "is_visible")
	  private Boolean visible=true;
		  
	  @JsonBackReference(value = "emp-news")
	  @ManyToOne(fetch = FetchType.LAZY, optional = false)
	  @JoinColumn(name = "publisher",nullable = false,insertable = false,updatable = false)
	  private Employee emp;
	  
	  @Column(name="publisher")
	  private Integer publisher;

}
