package com.eipulse.teamproject.controller.employeecontroller;

import com.eipulse.teamproject.dto.employeedto.EmpDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.service.employeeservice.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class EmpController {

    private EmployeeService employeeService;
    private EmployeeRepository empRepo;

    @Autowired
    public EmpController(EmployeeService employeeService,EmployeeRepository empRepo) {
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

    //查詢同部門員工
    @GetMapping("/employee/dept/{empId}")
    public ResponseEntity<?> findSameDeptEmp(@PathVariable Integer empId){
        return new ResponseEntity<>(employeeService.findSameDeptEmp(empId),HttpStatus.OK);
    }

    // 刪除員工
    @DeleteMapping("/employee/deleteEmp{id}")
    public ResponseEntity<?> deleteEmp(@PathVariable("id") Integer empId) {
        employeeService.deleteEmp(empId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //登入
    @PostMapping("/login")
    public ResponseEntity<?> loginCheck(@RequestBody EmpDTO empDTO, HttpSession httpSession) {
        EmpDTO employee = employeeService.empLogin(empDTO);
        httpSession.setAttribute("emp", employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    //忘記密碼

    @PostMapping("/login/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody EmpDTO empDTO, HttpSession httpSession) {
        int otpVal = new Random().nextInt(999999);
        httpSession.setAttribute("mailOtp", otpVal);
        EmpDTO result = employeeService.forgetPassword(empDTO, otpVal);
        return new ResponseEntity<>(result.getEmpId(), HttpStatus.OK);
    }

    //重設密碼
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

}
