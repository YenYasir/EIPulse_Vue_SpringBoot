package com.eipulse.teamproject.entity.evententity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.eipulse.teamproject.entity.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="event")
public class Event {
	
	  @Id
	  @Column(name="event_id")
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer eventId;
	  
	  @Column(name="title")
	  private String title;
	  
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start")
	private LocalDateTime start;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end")
	private LocalDateTime end;
	  
	@Column(name="description")
	  private String description;
  
    @JsonBackReference(value = "emp-calendar")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = false,insertable = false,updatable = false)
    private Employee emp;
    
    @Column(name="user_id")
    private Integer userId;


}
