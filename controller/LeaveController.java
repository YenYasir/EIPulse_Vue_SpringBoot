package com.eipulse.teamproject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eipulse.teamproject.dto.LeaveDTO;
import com.eipulse.teamproject.service.LeaveService;


@CrossOrigin
@RestController
@RequestMapping("/leaves")
public class LeaveController {
	
	private LeaveService leaveService;
	
	@Autowired
	public LeaveController(LeaveService leaveService) {
		this.leaveService = leaveService;
	}
	
	// 找出編碼表單
	@GetMapping("/{formId}")
	public ResponseEntity<?> findLeaveById(@PathVariable Integer formId) {
	    try {
//	        Leave leave = leaveService.findLeaveByFormId(formId);
	    	return new ResponseEntity<>(leaveService.findLeaveByFormId(formId),HttpStatus.OK);
//	        return (leave != null) ? ResponseEntity.ok(leave) : ResponseEntity.notFound().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	// 找出所有表單
	@GetMapping("/")
	public List<LeaveDTO> findAllLeaves(){
		return leaveService.findAllLeaves();
	}	
	
	// 新增表單
	@PostMapping("/")
	public LeaveDTO addLeave(@RequestBody LeaveDTO leaveDTO){
	  return leaveService.createAndUpdateLeave(leaveDTO);

	}
	
//	@PostMapping
//	public ResponseEntity<LeaveDto> createLeave(@RequestBody LeaveDto leaveDto) {
//	  return ResponseEntity.ok(leaveService.createLeave(leaveDto)); 
//	}
//	
	// 依照表單編號刪除休假表單
	@DeleteMapping("/{formId}") 
	public ResponseEntity<HttpStatus> deleteLeave(@PathVariable Integer formId) {
		try {
			leaveService.deleteByFormId(formId);
			// 成功處理了請求，但沒有需要返回的內容使用 NO_CONTENT,狀態碼 204
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			// 伺服器在處理請求時發生了內部錯誤,狀態碼 500
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@PutMapping("/{formId}")
//	public ResponseEntity<LeaveDto> updateLeave(@PathVariable Integer formId, @RequestBody LeaveDto dto) {
//	  return ResponseEntity.ok(leaveService.updateLeave(formId, dto));
//	} 
	
//	@PutMapping("/{formId}") 
//	public ResponseEntity<LeaveDto> updateLeave(@PathVariable Integer formId, @RequestBody LeaveDto leaveDto) {
//		
//	  LeaveDto existing = leaveService.findLeaveByFormId(formId);
//	  
//	  if (existing != null) {
//	    
//	    existing.setReason(leaveDto.getReason());
//	    existing.setDays(leaveDto.getDays());
//	    
//	    leaveService.save(existing);
//	    
//	    return ResponseEntity.ok(existing);
//
//	  } else {
//	    return ResponseEntity.notFound().build();
//	  }
//
//	}

	// 修改表單
	@PutMapping("/{formId}")
	public LeaveDTO updateLeave(@PathVariable Integer formId,@RequestBody LeaveDTO leaveDto) {
	  return leaveService.createAndUpdateLeave(leaveDto); 
	}
	
	// 依照表單類型找出表單
	@GetMapping("/type/{leaveType}")
	public List<LeaveDTO> findLeavesByFormType(@PathVariable Integer leaveType){
		return leaveService.findLeavesByLeaveType(leaveType);
	}
	
	
}
