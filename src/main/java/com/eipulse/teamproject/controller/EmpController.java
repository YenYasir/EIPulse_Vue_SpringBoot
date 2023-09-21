package com.eipulse.teamproject.controller;


import com.eipulse.teamproject.entitys.Employee;
import com.eipulse.teamproject.service.EmpServiceImp;
import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class EmpController {

    private EmpServiceImp empServiceImp;
    @Autowired
    public EmpController(EmpServiceImp empServiceImp) {
        this.empServiceImp = empServiceImp;
    }

    @PostMapping("/employee/post")
    public Employee postNewEmployeePage(@RequestBody Employee newEmployee) {
        return empServiceImp.saveEmp(newEmployee);
    }

    @GetMapping("/employee")
    public List<Employee> findAllEmp(){
        List<Employee>employees =empServiceImp.findEmpAll();
        if(employees !=null){
            return employees;
        }
        return null;
    }

    @GetMapping("/employee/{empId}")
    public Employee findByIdEmp(@PathVariable Integer empId){

        return empServiceImp.findEmpById(empId);
    }
    @GetMapping("/employee/editPage")
    public  String editEmployeePage(@RequestParam("empId")Integer empId,Model model){
        Employee employee = empServiceImp.findEmpById(empId);
        model.addAttribute("employee",employee);
        return "/employee/editPage";
    }
    @PutMapping("/employee/edit")
    public String editEmployee(@ModelAttribute("newEmployee")Employee newEmployee){
        empServiceImp.updateEmp(newEmployee.getEmpId(),newEmployee);
        return "/employee/allEmp";
    }
    @DeleteMapping("/employee/deleteEmp")
    public String deleteEmployee(@RequestParam("empId") Integer empId) {
        empServiceImp.deleteEmp(empId);
        return "/employee/allEmp";
    }

}
