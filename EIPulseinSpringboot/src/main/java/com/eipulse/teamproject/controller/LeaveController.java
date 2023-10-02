package com.eipulse.teamproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.teamproject.entitys.Leave;
import com.eipulse.teamproject.repository.LeaveRepository;
import com.eipulse.teamproject.service.LeaveServiceImpl;

@Controller
public class LeaveController {
	
	private LeaveServiceImpl leaveServiceImpl;
	
	@Autowired
	public LeaveController(LeaveServiceImpl leaveServiceImpl) {
		this.leaveServiceImpl = leaveServiceImpl;
	}
	
	@GetMapping("leave/add")//跳轉到新增頁面
	public String addLeave() {
		return "leave/addPage";
	}
	
	@PostMapping("leave/add")//新增休假
	public Leave dayoffApplication(@RequestBody Leave leave) {
		return leaveServiceImpl.saveLeave(leave);
	}
	
	
	@DeleteMapping("leave/delete/{formId}") //依照表單編號刪除休假表單
	public String deleteLeave(@RequestParam("formId") Integer formId) {
		leaveServiceImpl.deleteById(formId);
		return "leave/allLeave";
	}
	
	@GetMapping("/leave/find/{formId}") //依照表單編號找出休假表單
	public Optional<Leave> findLeaveById(@PathVariable Integer formId) {
		return leaveServiceImpl.findLeaveByFormId(formId);
	}
	
	@GetMapping("/leave/findAll")//找出所有表單
	public List<Leave> findAllLeave(){
		return leaveServiceImpl.findAllLeave();
	}
	
	@PutMapping("/leave/edit")
	public String editLeave(@ModelAttribute("newLeave") Leave leave) {
	    Leave updatedLeave = leaveServiceImpl.saveLeave(leave);	  
	    return "redirect:/leave/allLeave";  
	}
	
}
