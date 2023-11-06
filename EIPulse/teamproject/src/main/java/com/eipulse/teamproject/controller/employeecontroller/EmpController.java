package com.eipulse.teamproject.controller.employeecontroller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.dto.employeedto.EmpDTO;
import com.eipulse.teamproject.dto.employeedto.TitleMoveDTO;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.service.employeeservice.EmployeeService;

import jakarta.servlet.http.HttpSession;

@RestController
public class EmpController {

	private EmployeeService employeeService;
	private EmployeeRepository empRepo;

	@Autowired
	public EmpController(EmployeeService employeeService, EmployeeRepository empRepo) {
		this.employeeService = employeeService;
		this.empRepo = empRepo;
	}

	// 新增員工
	@PostMapping("/employee/add")
	public ResponseEntity<?> postEmp(@RequestBody EmpDTO employee) {
		employeeService.addEmp(employee);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 查詢單筆
	@GetMapping("/employee/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
	}

	// 查詢全部員工
	@GetMapping("/employee/findAll")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(employeeService.findAllEmp(), HttpStatus.OK);
	}

	// 查詢同部門員工
	@GetMapping("/employee/dept/{empId}")
	public ResponseEntity<?> findSameDeptEmp(@PathVariable Integer empId) {
		return new ResponseEntity<>(employeeService.findSameDeptEmp(empId), HttpStatus.OK);
	}

	// 刪除員工
	@DeleteMapping("/employee/deleteEmp{id}")
	public ResponseEntity<?> deleteEmp(@PathVariable("id") Integer empId) {
		employeeService.deleteEmp(empId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 登入
	@PostMapping("/login")
	public ResponseEntity<?> loginCheck(@RequestBody EmpDTO empDTO, HttpSession httpSession) {
		EmpDTO employee = employeeService.empLogin(empDTO);
		httpSession.setAttribute("emp", employee);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	// 忘記密碼

	@PostMapping("/login/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody EmpDTO empDTO, HttpSession httpSession) {
		int otpVal = new Random().nextInt(999999);
		httpSession.setAttribute("mailOtp", otpVal);
		EmpDTO result = employeeService.forgetPassword(empDTO, otpVal);
		return new ResponseEntity<>(result.getEmpId(), HttpStatus.OK);
	}

	// 重設密碼
	@PostMapping("/login/newPassword")
	public ResponseEntity<?> otpCheck(@RequestBody EmpDTO empDTO, HttpSession httpSession) {
		System.out.println(empDTO.getEmpId());
		System.out.println(empDTO.getNewPassword());
		System.out.println(empDTO.getOtpCheck());
		System.out.println(httpSession.getAttribute("mailOtp"));
		Integer mailOtp = (Integer) httpSession.getAttribute("mailOtp");
		if (mailOtp != null && mailOtp.equals(empDTO.getOtpCheck())) {
			employeeService.newPassword(empDTO);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 模糊搜尋名字
	@GetMapping("/findByName/{name}")
	public List<EmpDTO> findByName(@PathVariable("name") String name) {
		List<EmpDTO> list = employeeService.findByNameLikeSearch(name);
		return list;
	}

	// 更新員工
	@Transactional
	@PutMapping("/employee/updateEmp")
	public ResponseEntity<?> update(@RequestBody EmpDTO empDTO) {
		try {
			return new ResponseEntity<>(employeeService.updateEmp(empDTO), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// 變更員工職位
	@PutMapping("/employee/updateTitle")
	public ResponseEntity<?> updateEmpTitle(@RequestBody TitleMoveDTO dto) {
		try {
			return new ResponseEntity<>(employeeService.updateEmpTitle(dto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// 普通分頁
	@GetMapping("/employee/paged/{pageNumber}")
	@ResponseStatus(HttpStatus.OK) // 這裡設置返回的 HTTP 狀態碼為 200
	public Page<EmpDTO> getEmployeesByPage(@PathVariable Integer pageNumber) {
		return employeeService.findByPage(pageNumber);

	}

	// 模糊搜尋的分頁
	@GetMapping("/employee/paged/{name}/{pageNumber}")
	@ResponseStatus(HttpStatus.OK) // 這裡設置返回的 HTTP 狀態碼為 200
	public Page<EmpDTO> getByNamePage(@PathVariable String name, @PathVariable Integer pageNumber) {
		return employeeService.findByNamePage(pageNumber, name);

	}

	@GetMapping("/exceptForMyself")
	public ResponseEntity<?> exceptForMyself(@RequestParam Integer empId) {
		return new ResponseEntity<>(empRepo.exceptForMyself(empId), HttpStatus.OK);
	}

}
