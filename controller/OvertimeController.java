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
import com.eipulse.teamproject.dto.OvertimeDTO;
import com.eipulse.teamproject.service.OvertimeService;

@CrossOrigin
@RestController
@RequestMapping("/overtimes")
public class OvertimeController {

	private OvertimeService overService;
	
	@Autowired
	public OvertimeController(OvertimeService overService) {
		this.overService = overService;
	}

	// 透過表單編號查詢表單
	@GetMapping("{formId}")
	public ResponseEntity<?> findOvertimeById(@PathVariable Integer formId) {
	    try {
	    	return new ResponseEntity<>(overService.findOvertimeByFormId(formId),HttpStatus.OK);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// 找出所有表單
	@GetMapping("/")
	public List<OvertimeDTO> findAllOvertime(){
		return overService.findAllOvertimes();
	}
	
	// 新增加班單
	@PostMapping("/")
	public OvertimeDTO addOvertime(@RequestBody OvertimeDTO overtimeDTO) {
		return overService.createAndUpdateOvertime(overtimeDTO);
	}
	
	// 透過表單編號刪除
	@DeleteMapping("/{formId}") 
	public ResponseEntity<HttpStatus> deleteOvertime(@PathVariable Integer formId) {
		try {
			overService.deleteByFormId(formId);
			// 成功處理了請求，但沒有需要返回的內容使用 NO_CONTENT,狀態碼204
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			// 伺服器在處理請求時發生了內部錯誤,狀態碼 500
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 依照表單編號修改表單
	@PutMapping("/{formId}")
	public OvertimeDTO updateOvertime(@PathVariable Integer formId,@RequestBody OvertimeDTO overtimeDTO) {
	  return overService.createAndUpdateOvertime(overtimeDTO); 
	}
	
	// 依照加班類型查詢表單
	@GetMapping("/type/{overtimeType}")
	public List<OvertimeDTO> findLeavesByFormType(@PathVariable Integer overtimeType){
		return overService.findOvertimesByType(overtimeType);
	}
	
}
