//package com.eipulse.teamproject.controller;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.eipulse.teamproject.entitys.Dept;
//import com.eipulse.teamproject.entitys.Employee;
//import com.eipulse.teamproject.entitys.Role;
//import com.eipulse.teamproject.entitys.Title;
//import com.eipulse.teamproject.repository.EmployeeRepository;
//import com.eipulse.teamproject.service.EmpService;
//
//@RestController
//public class EmpTestController {
//
//	@Autowired
//	private EmpService empService;
//	@Autowired
//	private EmployeeRepository empRepository;
//
//	@PostMapping("/employee/insertEmp")
//	public List<Employee> addList(@RequestBody List<Employee> empList) {
//		return empRepository.saveAll(empList);
//
//	}
//
//	@PostMapping("/employee/post")
//	public Employee postNewEmployeePage(@RequestParam("idNumber") String idNumber,
//			@RequestParam("EmpName") String EmpName, @RequestParam("gender") String gender,
//			@RequestParam("birth") LocalDate birth, @RequestParam("tel") String tel,
//			@RequestParam("address") String address, @RequestParam("email") String email,
//			@RequestParam("empStat") String empStat, @RequestParam("hirdate") LocalDate hirdate,
////    									@RequestParam("leaveDate") LocalDate leaveDate,
//			@RequestParam("editDate") LocalDate editDate, @RequestParam("deptid") Dept dept,
//			@RequestParam("titleid") Title title, @RequestParam("roleid") Role role) {
//		Employee emp = new Employee();
//		emp.setIdNumber(idNumber);
//		emp.setEmpName(EmpName);
//		emp.setGender(gender);
//		emp.setBirth(birth);
//		emp.setPhone("1234");
//		emp.setTel(tel);
//		emp.setAddress(address);
//		emp.setEmail(email);
////		emp.setEmpStat(empStat);
////		emp.setHireDate(hirdate);
////		emp.setEditDate(editDate);
////		emp.setDept(dept);
////		emp.setTitle(title);
////		emp.setRole(role);
//		System.out.println(address);
//		return empRepository.save(emp);
//	}
//
//	@PostMapping("/addemp")
//	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee, @RequestBody Dept dept) {
//		try {
//			empService.createEmpId(employee, dept);
//			return new ResponseEntity<>(employee, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@PostMapping("/employee/post2")
//	public String postNewEmployeePage1(@ModelAttribute Employee newEmployee) {
//		empService.addEmp(newEmployee);
//		return "/employee/post";
//	}
//}
