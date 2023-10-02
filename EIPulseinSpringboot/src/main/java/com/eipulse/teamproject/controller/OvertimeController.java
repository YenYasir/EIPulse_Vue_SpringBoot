package com.eipulse.teamproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.entitys.Leave;
import com.eipulse.teamproject.entitys.Overtime;
import com.eipulse.teamproject.service.LeaveServiceImpl;
import com.eipulse.teamproject.service.OvertimeServiceImpl;

@RestController
public class OvertimeController {

	private OvertimeServiceImpl overtimeServiceImpl;
	
	@Autowired
	public OvertimeController(OvertimeServiceImpl overtimeServiceImpl) {
		this.overtimeServiceImpl = overtimeServiceImpl;
	}


	@GetMapping("/overtime/add")
	public String addOvertime() {
		return "overtime/addOvertimePage";
	}
	
	@PostMapping("/overtime/add")//申請加班
	public Overtime applyOvertime(@RequestBody Overtime overtime) {
		return overtimeServiceImpl.saveOvertime(overtime);
	}
	
	
	@DeleteMapping("/overtime/delete/{formId}") //刪除加班申請
	public String deleteOvertime(@RequestParam("formId") Integer formId) {
		overtimeServiceImpl.deleteById(formId);
		return "overtime/allOvertime";
	}
	
	@GetMapping("/overtime/find/{formId}") //依照表單編號找出加班表單
	public Optional<Overtime> findOvertime(@PathVariable Integer formId) {
		return overtimeServiceImpl.findByFormId(formId);
	}
	
	@GetMapping("/overtime/findAll")//找出所有表單
	public List<Overtime> findAllOvertime(){
		return overtimeServiceImpl.findAllOvertime();
	}
	
	@PutMapping("/overtime/edit")
	public String editOvertime(@ModelAttribute("newOvertime") Overtime overtime) {
		Overtime updatedOvertime = overtimeServiceImpl.saveOvertime(overtime);	    
	    return "redirect:/overtime/allOvertime"; 
	    
	}
	
}
