package com.eipulse.teamproject.controller;


import com.eipulse.teamproject.entity.Employee;
import com.eipulse.teamproject.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmpController {

    private EmpService empService;
    @Autowired
    public EmpController(EmpService empService) {
        this.empService = empService;
    }

    @PostMapping("/employee/post")
    public Employee postNewEmployeePage(@RequestBody Employee newEmployee) {
        return empService.saveEmp(newEmployee);
    }

    @GetMapping("/employee")
    public List<Employee> findAllEmp(){
        List<Employee>employees = empService.findEmpAll();
        if(employees !=null){
            return employees;
        }
        return null;
    }

    //OK
    @GetMapping("/employee/{empId}")
    public Employee findByIdEmp(@PathVariable Integer empId){

        return empService.findEmpById(empId);
    }
    @GetMapping("/employee/editPage")
    public  String editEmployeePage(@RequestParam("empId")Integer empId,Model model){
        Employee employee = empService.findEmpById(empId);
        model.addAttribute("employee",employee);
        return "/employee/editPage";
    }
    @PutMapping("/employee/edit")
    public String editEmployee(@ModelAttribute("newEmployee")Employee newEmployee){
        empService.updateEmp(newEmployee.getEmpId(),newEmployee);
        return "/employee/allEmp";
    }
    @DeleteMapping("/employee/deleteEmp")
    public String deleteEmployee(@RequestParam("empId") Integer empId) {
        empService.deleteEmp(empId);
        return "/employee/allEmp";
    }

}
