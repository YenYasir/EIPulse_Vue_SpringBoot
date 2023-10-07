package com.ispan.spirngboot3demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import com.ispan.spirngboot3demo.model.*;
import com.ispan.spirngboot3demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ispan.spirngboot3demo.service.EmployeeService;


@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private TitleRecordRepository titleRepo;
	@Autowired
	private ResignRepository resignRepo;
	@Autowired
	private DeptMoveRepository deptMoveRepo;
	@Autowired
	private PermissionRecordRepository permiRecordRepo;

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
	@Transactional
	@PutMapping("/emp/updateDept")
	public String updateEmpDept(@RequestParam Integer id,@RequestParam("newdept")Dept newdept) {
		Optional<Employee> optional = empRepo.findById(id);
		if(optional!=null) {
			Employee emp = optional.get();
			emp.setDept(newdept);
			return "修改成功";		
		}
		return "沒有這筆資料";
	}
	
	
	//升遷+調動紀錄>>update>>title，以title調動紀錄為主然後將after_title_id更新到員工的資料表
	//1 先確保員工存在
	//2 建立升遷紀錄
	//3 更新員工職位
	@Transactional
	@PostMapping("/emp/updateTitle")
	public ResponseEntity<String> insertTitleMoveRecord(@RequestParam("empId") Integer empId,
														@RequestParam("beforeTitleId") Integer beforeTitleId,
														@RequestParam("afterTitleId") Integer afterTitleId,
														@RequestParam("reason") String reason,
														@RequestParam("moveDate") LocalDate moveDate,
														@RequestParam("approver") String approver) {

		Optional<Employee> optional = empRepo.findById(empId);
		if(!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("沒有這筆資料");
		}

		Employee emp = optional.get();

		LocalDate nowTime = LocalDate.now();

		TitleMoveRecord record = new TitleMoveRecord();
		record.setBeforeTitleId(beforeTitleId);
		record.setAfterTitleId(afterTitleId);
		record.setReason(reason);
		record.setApprover(approver);
		record.setMoveDate(moveDate);
		record.setEditDate(nowTime);
		record.setEmployee(emp); // 設置Employee對象，而不是ID
		titleRepo.save(record);

		emp.setTitleId(afterTitleId);
		empRepo.save(emp);  // 保存更新的員工資料

		return ResponseEntity.ok("更新成功");
	}

	//年資>>計算後的value 用model帶到前端顯示
	@GetMapping("/emp/calculatejobage")
	public String getJobAgeById(@RequestParam Integer id,Model model ) {
		Optional<Employee> result = empRepo.findById(id);
		if(result.isPresent()) {
			Employee emp = result.get();
			 LocalDate hireDate = emp.getHirdate(); // 假設這個是LocalDate型態
			 System.out.println(hireDate);
		     LocalDate currentDate = LocalDate.now();
		     System.out.println(currentDate);

		        long monthsBetween = ChronoUnit.MONTHS.between(hireDate, currentDate);
		        double yearsOfService = monthsBetween / 12.0; // 計算年數
		        double roundedYears = Math.round(yearsOfService * 2) / 2.0; // 每六個月為0.5年，四捨五入
		        
		        model.addAttribute("jobage", roundedYears);
		        System.out.println(roundedYears);
		        return String.format("%d", (int)roundedYears);//返回對應的視圖名稱
		    } else {
		        return "employeeNotFound"; // 員工未找到，返回對應的視圖名稱
		    }
		}
	//轉跳人事界面和普通員工介面>>
//	@GetMapping("/emp/hrPage")
//	public String returnTohrPage(@RequestParam("id")Integer empid) {
//		Optional<Employee> empID = empRepo.findById(empid);
//		if(empID!=null) {
//			
//		}
//	}

	//權限變更紀錄>>
	@Transactional
	@PostMapping("/emp/updateDept")
	public ResponseEntity<String> insertPermissionMoveRecord(@RequestParam("empId") Integer empId,
													   @RequestParam("beforeRoleId") Integer beforeRoleId,
													   @RequestParam("afterRoleId") Integer afterRoleId,
													   @RequestParam("reason") String reason,
													   @RequestParam("moveDate") LocalDate moveDate,
													   @RequestParam("approver") String approver) {

		Optional<Employee> optional = empRepo.findById(empId);
		if(!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("沒有這筆資料");
		}

		Employee emp = optional.get();

		LocalDate nowTime = LocalDate.now();

		PermissionRecord record = new PermissionRecord();
		record.setBeforeRoleId(beforeRoleId);
		record.setAfterRoleId(afterRoleId);
		record.setReason(reason);
		record.setApprover(approver);
		record.setMoveDate(moveDate);
		record.setEditDate(nowTime);
		record.setEmployee(emp); // 設置Employee對象，而不是ID
		permiRecordRepo.save(record);

		emp.setRoleId(afterRoleId);
		empRepo.save(emp);  // 保存更新的員工資料

		return ResponseEntity.ok("更新成功");
	}


	//部門調動紀錄
	@Transactional
	@PostMapping("/emp/updateDept")
	public ResponseEntity<String> insertDeptMoveRecord(@RequestParam("empId") Integer empId,
														@RequestParam("beforeDeptId") Integer beforeDeptId,
														@RequestParam("afterDeptId") Integer afterDeptId,
														@RequestParam("reason") String reason,
														@RequestParam("moveDate") LocalDate moveDate,
														@RequestParam("approver") String approver) {

		Optional<Employee> optional = empRepo.findById(empId);
		if(!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("沒有這筆資料");
		}

		Employee emp = optional.get();

		LocalDate nowTime = LocalDate.now();

		DeptMoveRecord record = new DeptMoveRecord();
		record.setBeforeDeptId(beforeDeptId);
		record.setAfterDeptId(afterDeptId);
		record.setReason(reason);
		record.setApprover(approver);
		record.setMoveDate(moveDate);
		record.setEditDate(nowTime);
		record.setEmployee(emp); // 設置Employee對象，而不是ID
		deptMoveRepo.save(record);

		emp.setDeptId(afterDeptId);
		empRepo.save(emp);  // 保存更新的員工資料

		return ResponseEntity.ok("更新成功");
	}
	//員工離職紀錄
	@Transactional
	@PostMapping("/emp/updateDept")
	public ResponseEntity<String> insertResignRecord(@RequestParam("empId") Integer empId,
													 @RequestParam("reason") String reason,
													 @RequestParam("leaveDate") LocalDate leaveDate,
													 @RequestParam("approver") String approver) {

		Optional<Employee> optional = empRepo.findById(empId);
		if(!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("沒有這筆資料");
		}

		Employee emp = optional.get();

		LocalDate nowTime = LocalDate.now();

		ResignRecord record = new ResignRecord();
		record.setReason(reason);
		record.setLeaveDate(leaveDate);
		record.setEditDate(nowTime);
		record.setEmployee(emp); // 設置Employee對象，而不是ID
		resignRepo.save(record);

		emp.setEmpStat("離職");
		emp.setLeaveDate(leaveDate);
		empRepo.save(emp);  // 保存更新的員工資料

		return ResponseEntity.ok("更新成功");
	}

	
	//員工離職後記錄的table>>recycle
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
