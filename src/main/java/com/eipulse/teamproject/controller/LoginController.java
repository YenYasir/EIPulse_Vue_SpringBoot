package com.eipulse.teamproject.controller;


import com.eipulse.teamproject.dto.EmailDTO;
import com.eipulse.teamproject.dto.LoginDTO;
import com.eipulse.teamproject.dto.NewPasswordDTO;
import com.eipulse.teamproject.entity.Login;
import com.eipulse.teamproject.repository.LoginRepository;
import com.eipulse.teamproject.service.LoginService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
//@CrossOrigin(origins = "http://localhost:5173/")//cross
@RestController
public class LoginController {

    private LoginService loginService;
    private final LoginRepository loginRepository;

    @Autowired
    public LoginController(LoginService loginService,
                           LoginRepository loginRepository) {
        this.loginService = loginService;
        this.loginRepository = loginRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCheck(@RequestBody LoginDTO requestLogin,HttpSession httpSession){
        System.out.println(requestLogin);
        try{
            Login login = loginService.checkLogin(requestLogin.getEmpId(),requestLogin.getPassword());
            httpSession.setAttribute("emp",login);
//            httpSession.setAttribute("empId",login.getEmpId());
            Map<String, String> response = new HashMap<>();
            response.put("empId",Integer.toString(login.getEmpId()));
            response.put("empName",login.getEmployee().getEmpName());
            Login httpLogin = (Login) httpSession.getAttribute("emp");
            System.out.println("HTTP:"+httpLogin);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (UnsupportedOperationException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/login/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody EmailDTO requestEmail,HttpSession httpSession){
        int otpVal = new Random().nextInt(999999);
        httpSession.setAttribute("mailOtp",otpVal);
        System.out.println(httpSession.getId());
        try{
            Login login = loginService.forgetPassword(requestEmail.getEmail(),otpVal);
            System.out.println(login);
            return new ResponseEntity<>(login.getEmpId(), HttpStatus.OK);
        }catch (UnsupportedOperationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/login/newPassword")
    public ResponseEntity<?> otpCheck(@RequestBody NewPasswordDTO newPasswordDTO, HttpSession httpSession){
        try {
            Integer mailOtp=(Integer) httpSession.getAttribute("mailOtp");
            Login login = loginRepository.findById(newPasswordDTO.getEmpId()).get();
            System.out.println(httpSession.getId());

            System.out.println(mailOtp);
            System.out.println(newPasswordDTO);
            if(mailOtp != null && mailOtp.equals(newPasswordDTO.getOtpCheck()) ){
                System.out.println("OTP相同");
                loginService.newPassword(login, newPasswordDTO.getNewPassword());
            }else {
                System.out.println("OTP不同");

                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UnsupportedOperationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }
}
