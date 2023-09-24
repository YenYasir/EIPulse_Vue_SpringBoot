package com.ispan.spirngboot3demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.service.EmployeeService;


@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	//離職>>修改狀態
	@Transactional
	@PutMapping("/emp/updateStat")
	public String updateEmpStat(@RequestParam("id") Integer id,@RequestParam("newStat") String newStat) {
		Optional<Employee> optional = empRepo.findById(id);
		//因為資料庫更新後會回傳"1或0"筆資料已受影響，所以回傳型別是Integer
		if(optional!=null) {
			Employee emp = optional.get();
			emp.setEmpStat(newStat);
			return "修改成功";
		}
		return "沒有這筆資料";
	}
	
	//換部門>>update部門
	
	
	//升遷>>update>>title
	
	//年資>>計算後的value 用model帶到前端顯示
	@GetMapping("/emp/calculatejobage")
	public String getJobAgeById(@RequestParam Integer id,Model model ) {
		Optional<Employee> result = empRepo.findById(id);
		if(result.isPresent()) {
			Employee emp = result.get();
			 LocalDate hireDate = emp.getHirdate(); // 假設這個是LocalDate型態
		        LocalDate currentDate = LocalDate.now();

		        long monthsBetween = ChronoUnit.MONTHS.between(hireDate, currentDate);
		        double yearsOfService = monthsBetween / 12.0; // 計算年數
		        double roundedYears = Math.round(yearsOfService * 2) / 2.0; // 每六個月為0.5年，四捨五入

		        model.addAttribute("jobage", roundedYears);
		        return "/emp/jobage"; //返回對應的視圖名稱
		    } else {
		        return "employeeNotFound"; // 員工未找到，返回對應的視圖名稱
		    }
		}
	//轉跳人事界面和普通員工介面>>
	
	//role 系統管理員、HR、一般員工
	
	//權限變更紀錄>>建立新的table
	
	//部門調動紀錄
	
	//員工調整紀錄
	
	//員工離職後記錄的table>>recycle
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
