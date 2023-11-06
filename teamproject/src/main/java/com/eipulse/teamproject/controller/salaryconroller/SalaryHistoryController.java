package com.eipulse.teamproject.controller.salaryconroller;


import com.eipulse.teamproject.dto.salarydto.SalaryHistoryDto;
import com.eipulse.teamproject.service.salaryservice.SalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class SalaryHistoryController {
	private SalaryHistoryService slHistoryService;

	@Autowired
	public SalaryHistoryController(SalaryHistoryService slHistoryService) {
		this.slHistoryService = slHistoryService;
	}

	@PostMapping("/salaryHistory/new")
	public ResponseEntity<?> setSalaryHistory(@RequestBody SalaryHistoryDto historyDto) {
		
		SalaryHistoryDto result = slHistoryService.setHistory(historyDto);
		if(result != null) {
			return ResponseEntity.status(HttpStatus.OK).body("新增成功");
		}return ResponseEntity.status(HttpStatus.ACCEPTED).body("薪資未調整，不用更新");
		
	}

	// 查詢單一員工異動紀錄
	@GetMapping("/salaryHistory/{empId}")
	public ResponseEntity<?> findSalaryHistory(@PathVariable Integer empId) {
		try {
			List<SalaryHistoryDto> results = slHistoryService.findHistoryByEmpId(empId);
			
;			return new ResponseEntity<>(results, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
