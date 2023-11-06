//package com.eipulse.teamproject.controller.salaryconroller;
//
//import java.time.LocalDate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//@Controller
//public class EmpController {
//	@Autowired
//	private EmpService empService;
//
//	@ResponseBody
//	@GetMapping("/payroll/find")
//	private List<Employee> findEmpForSalary(@RequestParam LocalDate date){
//		return empService.findHireDateBefore(date);
//
//	}
//
//}
