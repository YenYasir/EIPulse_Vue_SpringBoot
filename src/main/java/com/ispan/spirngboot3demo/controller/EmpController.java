package com.ispan.spirngboot3demo.controller;


import com.ispan.spirngboot3demo.model.EmpDTO;
import com.ispan.spirngboot3demo.model.Employee;
import com.ispan.spirngboot3demo.model.TitleDTO;
import com.ispan.spirngboot3demo.model.TitleMoveDTO;
import com.ispan.spirngboot3demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmpController {

    private EmployeeService employeeService;

    @Autowired
    public EmpController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 新增員工
    @PostMapping("/employee/add")
    public ResponseEntity<?> postEmp(@RequestBody EmpDTO employee){
        System.out.println(employee);
        try {
            employeeService.addEmp(employee);
            System.out.println(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 查詢單筆
    @GetMapping("/employee/{id}")
    public ResponseEntity<?>findById(@PathVariable Integer id){
        return new ResponseEntity<>(employeeService.findById(id),HttpStatus.OK);
    }
    // 查詢全部員工
    @GetMapping("/employee/findAll")
    public ResponseEntity<?> findAll(){
        try{
            return new ResponseEntity<>(employeeService.findAllEmp(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    // 模糊收尋名字
    @GetMapping("/findByName/{name}")
    public List<EmpDTO> findByName(@PathVariable("name") String name){
        List<EmpDTO> list = employeeService.findByNameLikeSearch(name);
        return list;
    }

    // 更新員工
    @Transactional
    @PutMapping("/employee/updateEmp")
    public ResponseEntity<?> update(@RequestBody EmpDTO empDTO){
      try {
          return new ResponseEntity<>(employeeService.updateEmp(empDTO),HttpStatus.OK);
      }catch (Exception e){
          return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
      }
    }

    // 變更員工職位
    @PutMapping("/employee/updateTitle")
    public ResponseEntity<?> updateEmpTitle(@RequestBody TitleMoveDTO dto){
        try {
            return new ResponseEntity<>(employeeService.updateEmpTitle(dto),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // 刪除員工
    @DeleteMapping("/employee/deleteEmp/{id}")
    public String deleteEmp(@PathVariable("id")Integer empId){
        employeeService.deleteEmp(empId);
        return "delete OK";
    }

    // 普通分頁
    @GetMapping("/employee/paged/{pageNumber}")
    @ResponseStatus(HttpStatus.OK)  // 這裡設置返回的 HTTP 狀態碼為 200
    public Page<EmpDTO> getEmployeesByPage(@PathVariable Integer pageNumber) {
        return employeeService.findByPage(pageNumber);

    }
    // 模糊收尋的分頁
    @GetMapping("/employee/paged/{name}/{pageNumber}")
    @ResponseStatus(HttpStatus.OK)  // 這裡設置返回的 HTTP 狀態碼為 200
    public Page<EmpDTO> getByNamePage(@PathVariable String name,@PathVariable Integer pageNumber) {
        return employeeService.findByNamePage(pageNumber,name);

    }


}
