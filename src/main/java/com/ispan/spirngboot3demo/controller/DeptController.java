package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.DeptDTO;
import com.ispan.spirngboot3demo.model.EmpDTO;
import com.ispan.spirngboot3demo.service.DeptService;
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
    @PostMapping(path="/dept/add")
    public ResponseEntity<?> addDept(@RequestBody DeptDTO deptDTO){
        System.out.println(deptDTO.getDeptName());
        System.out.println(deptDTO.getDeptOffice());
        try {
            deptService.addDept(deptDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 查詢單筆
    @GetMapping("/dept/{id}")
    public ResponseEntity<?>findById(@PathVariable Integer id){
        return new ResponseEntity<>(deptService.findById(id),HttpStatus.OK);
    }

    // 查詢全部門
    @GetMapping("/dapt/findAll")
    public List<Dept> findAll(){
        return deptService.findAllDept();
    }

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
    public String deleteDept(@PathVariable("id")Integer id) {
        deptService.deleteDept(id);
        return "delete OK";
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
