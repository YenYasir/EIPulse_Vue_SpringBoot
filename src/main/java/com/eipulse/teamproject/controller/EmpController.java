package com.eipulse.teamproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.entitys.Employee;
import com.eipulse.teamproject.serviceimp.EmpServiceImp;

@RestController
public class EmpController {

//	@Autowired
//	private EmployeeRepository empRepository;

	private EmpServiceImp empServiceImp;

	@Autowired
	public EmpController(EmpServiceImp empServiceImp) {
		this.empServiceImp = empServiceImp;
	}
//
//	// 離職>>修改狀態
//	@Transactional
//	@PutMapping("/employee/updateStat")
//	public String updateEmpStat(@RequestParam("id") Integer id, @RequestParam("newStat") String newStat) {
//		Optional<Employee> optional = empRepository.findById(id);
//		// 因為資料庫更新後會回傳"1或0"筆資料已受影響，所以回傳型別是Integer
//		if (optional != null) {
//			Employee emp = optional.get();
//			emp.setEmpStat(newStat);
//			return "修改成功";
//		}
//		return "沒有這筆資料";
//	}
//
//	// 換部門>>update部門
//	@Transactional
//	@PutMapping("/employee/updateDept")
//	public String updateEmpDept(@RequestParam Integer id, @RequestParam("newdept") Dept newdept) {
//		Optional<Employee> optional = empRepository.findById(id);
//		if (optional != null) {
//			Employee emp = optional.get();
//			emp.setDept(newdept);
//			return "修改成功";
//		}
//		return "沒有這筆資料";
//	}
//
//	// 升遷>>update>>title
//	@Transactional
//	@PutMapping("/employee/updateTitle")
//	public String updateEmpTitle(@RequestParam Integer id, @RequestParam("newtitle") Title newtitle) {
//		Optional<Employee> optional = empRepository.findById(id);
//		if (optional != null) {
//			Employee emp = optional.get();
//			emp.setTitle(newtitle);
//			return "修改成功";
//		}
//		return "沒有這筆資料";
//	}
//
//	// 年資>>計算後的value 用model帶到前端顯示
//	@GetMapping("/employee/calculatejobage")
//	public String getJobAgeById(@RequestParam Integer id, Model model) {
//		Optional<Employee> result = empRepository.findById(id);
//		if (result.isPresent()) {
//			Employee emp = result.get();
//			LocalDate hireDate = emp.getHireDate(); // 假設這個是LocalDate型態
//			System.out.println(hireDate);
//			LocalDate currentDate = LocalDate.now();
//			System.out.println(currentDate);
//
//			long monthsBetween = ChronoUnit.MONTHS.between(hireDate, currentDate);
//			double yearsOfService = monthsBetween / 12.0; // 計算年數
//			double roundedYears = Math.round(yearsOfService * 2) / 2.0; // 每六個月為0.5年，四捨五入
//
//			model.addAttribute("jobage", roundedYears);
//			System.out.println(roundedYears);
//			return String.format("%d", (int) roundedYears);// 返回對應的視圖名稱
//		} else {
//			return "employeeNotFound"; // 員工未找到，返回對應的視圖名稱
//		}
//	}

	@PostMapping("/employee/post")
	public Employee postNewEmployeePage(@RequestBody Employee newEmployee) {
		return empServiceImp.saveEmp(newEmployee);
	}

	@GetMapping("/employee")
	public List<Employee> findAllEmp() {
		List<Employee> employees = empServiceImp.findEmpAll();
		if (employees != null) {
			return employees;
		}
		return null;
	}

	// OK
	@GetMapping("/employee/{empId}")
	public Employee findByIdEmp(@PathVariable Integer empId) {

		return empServiceImp.findEmpById(empId);
	}

	@GetMapping("/employee/editPage")
	public String editEmployeePage(@RequestParam("empId") Integer empId, Model model) {
		Employee employee = empServiceImp.findEmpById(empId);
		model.addAttribute("employee", employee);
		return "/employee/editPage";
	}

	@PutMapping("/employee/edit")
	public String editEmployee(@ModelAttribute("newEmployee") Employee newEmployee) {
		empServiceImp.updateEmp(newEmployee.getEmpId(), newEmployee);
		return "/employee/allEmp";
	}

	@DeleteMapping("/employee/deleteEmp")
	public String deleteEmployee(@RequestParam("empId") Integer empId) {
		empServiceImp.deleteEmp(empId);
		return "/employee/allEmp";
	}

}
