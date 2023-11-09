package com.eipulse.teamproject.controller.salaryconroller;

import com.eipulse.teamproject.dto.salarydto.CheckEmpDto;
import com.eipulse.teamproject.dto.salarydto.SalaryInfoDto;
import com.eipulse.teamproject.service.salaryservice.EmpSalaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmpSalaryInfoController {

    private EmpSalaryInfoService empSlService;

    @Autowired
    public EmpSalaryInfoController(EmpSalaryInfoService empSlService) {
        this.empSlService = empSlService;
    }

    // 檢查員工基本資料是否建立
    @GetMapping("/payroll/checkEmpId")
    public ResponseEntity<?> checkEmp(@RequestParam Integer empId){
    	CheckEmpDto findById = empSlService.findById(empId);
    	  return new ResponseEntity<>(findById, HttpStatus.OK);
    }
    
    // 檢查員工薪資資料是否建立
    @GetMapping("/payroll/checkInfo")
    public ResponseEntity<?> checkInfo(@RequestParam Integer empId){
  	  return new ResponseEntity<>(empSlService.existsById(empId), HttpStatus.OK);
    }

    //新增 ok
    @PostMapping("/payroll/newSalaryInfo")
    public ResponseEntity<?> saveOrUpdate(@RequestBody SalaryInfoDto salaryInfoDto) {
        String status = empSlService.createNewEmpSalayInfo(salaryInfoDto);
            return ResponseEntity.status(HttpStatus.OK).body("成功");
        }
       

    // 查詢單一員工 OK
    @GetMapping("/payroll/{empId}")
    public ResponseEntity<?> findSalaryInfoById(@PathVariable Integer empId) {
        SalaryInfoDto result = empSlService.findInfoById(empId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {

            return new ResponseEntity<>("查無此員工資料", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/payroll/byName")
    public ResponseEntity<?> findByName(@RequestParam String empName) {
        List<SalaryInfoDto> result = empSlService.findInfoByName(empName);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {

            return new ResponseEntity<>("查無相關員工", HttpStatus.BAD_REQUEST);
        }
    }



    // 分頁
    @GetMapping("/payroll/page/{number}")
    public Page<SalaryInfoDto> page(@PathVariable Integer number) {
        return empSlService.findByPage(number);
    }
    
    // 分頁byName
    @GetMapping("/payroll/page/{name}/{pageNumber}")
    public Page<SalaryInfoDto> page(@PathVariable String name,@PathVariable Integer pageNumber) {
        return empSlService.findNameLikeByPage(name,pageNumber);

    }

}
