package com.eipulse.teamproject.controller.salaryconroller;

import com.eipulse.teamproject.service.salaryservice.SalaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaryDetailController {

	private SalaryDetailService detailService;

	@Autowired
	public SalaryDetailController(SalaryDetailService detailService) {
		this.detailService = detailService;
	}
	
	//單號找明細ok
	@GetMapping("/payroll/detail/{id}")
	public ResponseEntity<?> findOneDetail(@PathVariable Integer id){
		return new ResponseEntity<>(detailService.findById(id), HttpStatus.OK);
	}

}
