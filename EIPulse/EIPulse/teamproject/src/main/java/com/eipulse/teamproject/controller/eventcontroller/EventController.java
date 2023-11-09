package com.eipulse.teamproject.controller.eventcontroller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import com.eipulse.teamproject.entity.evententity.Event;
import com.eipulse.teamproject.service.eventservice.EventService;

@RestController
@RequestMapping("/events")
public class EventController {
	
	 
	private EventService eventService;
	 
	 @Autowired
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}
	 
//	  按照時間讀取全部活動
	 @GetMapping("/")
	 public ResponseEntity<?> findAllEvent() {
	        return new ResponseEntity<>(eventService.findAllEventByTime(),HttpStatus.OK);
	    }
	 
//	 @GetMapping("/")
//	 public List<Event> getEvents(Integer userId) {
//		  return eventService.findAllEventBySelf(userId); 
//		}
	 	 
	 // 模糊搜尋活動
	 @GetMapping("/search")
	 public ResponseEntity<?> searchEvents(@RequestParam String keyword) {
	        List<Event> events = eventService.findEventsByKeyword(keyword);
	        return new ResponseEntity<>(events, HttpStatus.OK);
	    }


	  // 新增活動
	  @PostMapping("/")
	  public ResponseEntity<?> addEvent(@RequestBody Event event) {
	    eventService.saveEvent(event);
	    return new ResponseEntity<>(HttpStatus.OK);
	  }  
	  

	  // 根據id找出活動
	  @GetMapping("/{id}")
	    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
	        Optional<Event> optional = eventService.findEventById(id);
	        if (optional.isPresent()) {
	            return ResponseEntity.ok(optional.get());
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	  // 删除活動
	  @DeleteMapping("/{id}")
	  public ResponseEntity<?> deleteEvent(@PathVariable Integer id) {
	     eventService.deleteEventById(id);
	     return new ResponseEntity<>(HttpStatus.OK);
	  }
	  
	  // 讀取當前之後的活動
	  @GetMapping("/aftertoday")
	    public List<Event> getAllEventsAfterToday() {
	        LocalDateTime today = LocalDateTime.now(); // 獲取當前時間
	        return eventService.findAllEventsAfterToday(today);
	    }
	  
	  // 依照活動id修改內容
//	  @PutMapping("/{id}")
//	    public void updateEventDetails(@PathVariable Integer id, @RequestParam String title, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end, @RequestParam String description) {
//	        eventService.updateEventDetails(id, title, start, end, description);
//	   }
	  
	// 依照活動ID修改活動
	  @PutMapping("/{id}")
	  public void updateEvent(@PathVariable Integer id, @RequestBody Event event) {
	    event.setEventId(id); 
	    eventService.updateEvent(id, event);
	  }

}
