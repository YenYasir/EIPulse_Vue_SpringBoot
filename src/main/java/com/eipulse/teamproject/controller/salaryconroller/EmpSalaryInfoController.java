package com.eipulse.teamproject.controller.salaryconroller;
import com.eipulse.teamproject.dto.salarydto.CheckEmpIdResponse;
import com.eipulse.teamproject.dto.salarydto.SalaryInfoDto;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.salaryentity.EmpSalaryInfo;
import com.eipulse.teamproject.service.salaryservice.EmpSalaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EmpSalaryInfoController {

    private EmpSalaryInfoService empSlService;

    @Autowired
    public EmpSalaryInfoController(EmpSalaryInfoService empSlService) {
        this.empSlService = empSlService;
    }

    // 輸入empid確認是否已建立該筆員工基本資料 ok(前端已測試,但查無資料的錯誤訊息尚無法拿到)
    @GetMapping("/payroll/checkEmpId")
    public ResponseEntity<?> checkEmp(@RequestParam Integer empId) {
        CheckEmpIdResponse checkResult = new CheckEmpIdResponse();
        Employee emp = empSlService.findById(empId);
        Boolean isExist = empSlService.existsById(empId);
        if (emp != null & (!isExist)) {
            checkResult.setEmpId(empId);
            checkResult.setEmpName(emp.getEmpName());

            return new ResponseEntity<CheckEmpIdResponse>(checkResult, null, HttpStatus.OK);
        } else if (isExist)
            return new ResponseEntity<String>("已建立薪資資訊", null, HttpStatus.ACCEPTED);
        else {
            return new ResponseEntity<String>("尚未建立員工資料", null, HttpStatus.BAD_REQUEST);
        }

    }

    //新增 ok
    @PostMapping("/payroll/newSalaryInfo")
    public ResponseEntity<?> saveOrUpdate(@RequestBody SalaryInfoDto salaryInfoDto) {
        SalaryInfoDto result = empSlService.createNewEmpSalayInfo(salaryInfoDto);
        System.out.println(result.getEmpName());
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body("成功");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("失敗");

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

}
