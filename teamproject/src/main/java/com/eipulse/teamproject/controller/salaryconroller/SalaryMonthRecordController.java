package com.eipulse.teamproject.controller.salaryconroller;
import com.eipulse.teamproject.dto.salarydto.SalaryTrialDto;
import com.eipulse.teamproject.service.salaryservice.SalaryMonthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
public class SalaryMonthRecordController {

	private SalaryMonthRecordService slMonthService;

	@Autowired
	public SalaryMonthRecordController(SalaryMonthRecordService slMonthService) {
		this.slMonthService = slMonthService;
	}

	// 產生試算&存入資料庫
	@GetMapping("/payroll/salaryTrial/generate")
	public ResponseEntity<?> generateSalaryTrial(@RequestParam LocalDate date) {
		return new ResponseEntity<>(slMonthService.saveMonthRecordAndDetail(), HttpStatus.OK);

	}
	// 找當月份紀錄與明細單
	@GetMapping("/payroll/salaryTrial/findAll")
	public ResponseEntity<?>  findAll(@RequestParam LocalDate date){
		
		return new ResponseEntity<>(slMonthService.findAllByMonthAndYear(date),HttpStatus.OK);
	}
	
	// 透過recordId找
	@GetMapping("/payroll/salaryTrial/{id}")
    public ResponseEntity<?>  findById(@PathVariable Integer id){
		return new ResponseEntity<>(slMonthService.findById(id),HttpStatus.OK);
	}

	
	
	// 更新單一員工紀錄表與明細
	@PostMapping("/payroll/record/update")
	public ResponseEntity<?>  update(@RequestBody SalaryTrialDto finalDto) {
		return new ResponseEntity<>(slMonthService.update(finalDto),HttpStatus.OK);
	}


	// 員工查詢歷史薪資明細/月紀錄
	@GetMapping("/payroll/record/{empId}")
	public ResponseEntity<?>  findAllByEmpId(@PathVariable Integer empId) {
		return new ResponseEntity<>(slMonthService.findAllByEmpId(empId),HttpStatus.OK);
	}

}
