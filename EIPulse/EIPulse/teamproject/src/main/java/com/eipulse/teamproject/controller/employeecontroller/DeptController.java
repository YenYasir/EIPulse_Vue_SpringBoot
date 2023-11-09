package com.eipulse.teamproject.controller.employeecontroller;


import com.eipulse.teamproject.dto.employeedto.DeptDTO;
import com.eipulse.teamproject.entity.employee.Dept;
import com.eipulse.teamproject.service.employeeservice.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // 更新部門
    @Transactional
    @PutMapping("/dept/update")
    public ResponseEntity<?> update(@RequestBody DeptDTO dto){
        try {
            return new ResponseEntity<>(deptService.update(dto),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 刪除員工
    @DeleteMapping("/dept/delete/{id}")
    public ResponseEntity<?> deleteDept(@PathVariable("id") Integer id) {
        deptService.deleteDept(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

   // 分頁功能 by name
    @GetMapping("/dept/paged/{name}/{pageNumber}")
    @ResponseStatus(HttpStatus.OK)  // 這裡設置返回的 HTTP 狀態碼為 200
    public Page<DeptDTO> getByNamePage(@PathVariable String name, @PathVariable Integer pageNumber) {
        return deptService.findByNamePage(pageNumber,name);
    }
    // 普通分頁
    @GetMapping("/dept/paged/{pageNumber}")
    @ResponseStatus(HttpStatus.OK)  // 這裡設置返回的 HTTP 狀態碼為 200
    public Page<DeptDTO> getEmployeesByPage(@PathVariable Integer pageNumber) {
        return deptService.findByPage(pageNumber);
    }
}
