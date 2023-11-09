package com.eipulse.teamproject.dto.eventdto;

import java.time.LocalDateTime;

import com.eipulse.teamproject.entity.evententity.Event;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class EventDTO {
	
	private Integer eventId;
	private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private String description;
    private Integer userId;
    private String empName;
    
	public EventDTO(Event event) {
		
		this.eventId =event.getEventId();
		this.title = event.getTitle();
		this.start = event.getStart();
		this.end = event.getEnd();
		this.description = event.getDescription();
		this.userId = event.getUserId();
		this.empName =event.getEmp().getEmpName();
	}
	
	public EventDTO(){
		
	}
    
    

}
