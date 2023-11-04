package com.eipulse.teamproject.controller.employeecontroller;


import com.eipulse.teamproject.dto.employeedto.DeptDTO;
import com.eipulse.teamproject.entity.employee.Dept;
import com.eipulse.teamproject.service.employeeservice.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DeptController {

    private DeptService deptService;

    @Autowired
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    // 新增部門
    @PostMapping(path = "/dept/add")
    public ResponseEntity<?> addDept(@RequestBody DeptDTO deptDTO) {
        deptService.addDept(deptDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 查詢單筆
    @GetMapping("/dept/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(deptService.findById(id), HttpStatus.OK);
    }

    // 查詢全部門
    @GetMapping("/dapt/findAll")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(deptService.findAllDept(), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/dept/update")
    public String update(@RequestParam("deptName") String deptName,
                         @RequestParam("deptOffice") String deptOffice,
                         @RequestParam("id") Integer deptId) {
        Optional<Dept> optional = Optional.ofNullable(deptService.findById(deptId));
        if (optional != null) {
            Dept dept = optional.get();
            dept.setDeptName(deptName);
            dept.setDeptOffice(deptOffice);
            return "update OK";
        }
        return "update False";
    }

    // 刪除員工
    @DeleteMapping("/dept/delete{id}")
    public ResponseEntity<?> deleteDept(@PathVariable("id") Integer id) {
        deptService.deleteDept(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
