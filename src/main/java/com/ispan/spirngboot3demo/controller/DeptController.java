package com.ispan.spirngboot3demo.controller;

import com.ispan.spirngboot3demo.model.Dept;
import com.ispan.spirngboot3demo.model.DeptDTO;
import com.ispan.spirngboot3demo.service.DeptService;
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
    public String update(@RequestParam("deptName") String deptName,
                         @RequestParam("deptOffice") String deptOffice,
                         @RequestParam("id") Integer deptId){
        Optional<Dept> optional = Optional.ofNullable(deptService.findById(deptId));
        if (optional!=null){
            Dept dept = optional.get();
            dept.setDeptName(deptName);
            dept.setDeptOffice(deptOffice);
            return  "update OK";
        }
        return  "update False";
    }

    // 刪除員工
    @DeleteMapping("/dept/delete{id}")
    public String deleteDept(@PathVariable("id")Integer id) {
        deptService.deleteDept(id);
        return "delete OK";
    }

}
