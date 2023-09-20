package com.eipulse.teamproject.controller;


import com.eipulse.teamproject.entitys.Employee;
import com.eipulse.teamproject.service.EmpServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmpController {

    private EmpServiceImp empServiceImp;
    @Autowired
    public EmpController(EmpServiceImp empServiceImp) {
        this.empServiceImp = empServiceImp;
    }

    @PostMapping("/employee/post")
    public String postNewEmployeePage(@ModelAttribute Employee newEmployee) {
        empServiceImp.saveEmp(newEmployee);
        return "/employee/post";
    }

    @GetMapping("/employee/findAllEmp")
    public String findAllEmpPage(Model model){
        List<Employee> employeeList= empServiceImp.findEmpAll();
        model.addAttribute("employees",employeeList);
        return "/employee/allEmp";
    }

    @GetMapping("/employee/findByIdEmp")
    public String findByIdEmp(@RequestParam("empId")Integer empId,Model model){
        Employee employee = empServiceImp.findEmpById(empId);
        model.addAttribute("employee",employee);
        return "/employee/findByIdEmp";
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
