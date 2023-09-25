package com.eipulse.teamproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eipulse.teamproject.entitys.Login;
import com.eipulse.teamproject.service.LoginService;


import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/logins")
	public String index() {
		return "index";
		
	}
	
	@GetMapping("/employee/login")
	public String mainPage() {
		return "employee/mainPage";
	}
	
	@PostMapping("/employee/login")
	public String checkEmpLogin(
			@RequestParam("EmpId") Integer EmpId,
			@RequestParam("PassWord") String PassWord,
		HttpSession httpsession,
		Model model) {
		
		Login loginresult = loginService.checkLogin(EmpId,PassWord);
		if (loginresult!=null) {
			System.out.println("登入成功");
			httpsession.setAttribute("loginEmp", loginresult);
			return "employee/mainPage";
		}else {
			System.out.println("登入失敗");
			model.addAttribute("loginFail", "帳號密碼錯誤");
			return "index";
		}
		
	}
	
	@GetMapping("/employee/forgotpassword")
	public String forgotpwdPage() {
		return "employee/forgotpwdPage";
	}
	
	@PostMapping("/employee/forgotpassword")
	public String forgotpassowrd(@RequestParam("Email") String email,Model model) {
		
		
		
	
	
	return "index";
	}
	
}
