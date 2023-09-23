package com.ispan.spirngboot3demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ispan.spirngboot3demo.repository.EmployeeRepository;
import com.ispan.spirngboot3demo.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	@Autowired
	private EmployeeRepository empRepo;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
