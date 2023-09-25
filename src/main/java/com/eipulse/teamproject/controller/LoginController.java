package com.eipulse.teamproject.controller;


import com.eipulse.teamproject.entitys.Login;
import com.eipulse.teamproject.service.LoginServiceImp;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;
@Controller
public class LoginController {

    private LoginServiceImp loginServiceImp;

    @Autowired
    public LoginController(LoginServiceImp loginServiceImp) {
        this.loginServiceImp = loginServiceImp;
    }

    @PostMapping("/login/forgetPassword")
    public String  forgetPassword(@RequestParam("email")String email, HttpSession httpSession){
        int otpVal = new Random().nextInt(999999);
        Login login = loginServiceImp.forgetPassword(email,otpVal);
        httpSession.setAttribute("OTP",otpVal);
        httpSession.setAttribute("login",login);
        return "/login/newPassword";
    }
    @PostMapping("/login/newPassword")
    public String otpCheck(@RequestParam("otpCheck")Integer otpCheck,@RequestParam("newPassword")String newPassword,HttpSession httpSession){
        Integer mailOtp=(Integer) httpSession.getAttribute("OTP");
        Login login =(Login)httpSession.getAttribute("login");
        if(mailOtp != null && mailOtp.equals(otpCheck)){
            loginServiceImp.newPassword(login,newPassword);
        }else {
            return "錯誤";
        }
        return "index";
    }
}
