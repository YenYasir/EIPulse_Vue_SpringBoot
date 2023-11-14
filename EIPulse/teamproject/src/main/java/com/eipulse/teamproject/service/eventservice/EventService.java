package com.eipulse.teamproject.service.eventservice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.teamproject.entity.evententity.Event;
import com.eipulse.teamproject.repository.eventrepository.EventRepository;

@Service
@Transactional
public class EventService {
	
	private EventRepository eventRepo;
	
    @Autowired
    public EventService (EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }
    
//    // 依照開始時間顯示活動
    public List<Event> findAllEventByTime() {
    	return eventRepo.findAllByStartDate();  	
      }
    

    
//  依照開始時間讀取自身的所有活動 
 public List<Event> findAllEventBySelf(Integer userId) {
 	return eventRepo.findByUserId(userId);  	
   }   
    
    // 使用模糊搜尋查詢活動
    public List<Event> findEventsByKeyword(String keyword) {
        return eventRepo.findEventsByKeyword(keyword);
    }
    
    
    // 新增及修改活動
    public void saveEvent(Event event) {
    	eventRepo.save(event);
    }
    
    // 根據id找出活動
    public Optional<Event> findEventById(Integer id) {
        return eventRepo.findById(id);
    }
      
    // 根據id刪除活動
    public void deleteEventById(Integer eventId) {
        eventRepo.deleteById(eventId);
    }
    
    // 找出當前時間之後的活動
    public List<Event> findAllEventsAfterToday(LocalDateTime today) {
        return eventRepo.findAllEventsAfterToday(today);
    }
    
//    // 依照活動ID修改活動詳情
//    public void updateEventDetails(Integer eventId, String title, LocalDateTime start, LocalDateTime end, String description) {
//        eventRepo.updateEventDetails(eventId, title, start, end, description);
//    }
    
 // 依照活動ID修改活動
    public void updateEvent(Integer id, Event event) {
    	  eventRepo.updateEvent(id, event); 
    	}


}
