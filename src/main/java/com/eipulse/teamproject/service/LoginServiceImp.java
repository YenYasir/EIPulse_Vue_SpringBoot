package com.eipulse.teamproject.service;

import com.eipulse.teamproject.entitys.Login;
import com.eipulse.teamproject.repository.LoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoginServiceImp implements LoginService {
    private LoginRepository loginRepository;
    private PasswordEncoder passwordEncoder;
    private JavaMailSender javaMailSender;


    @Autowired
    public LoginServiceImp(LoginRepository loginRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }


    @Override
    public Login checkLogin(Integer empId, String password) {

        Login empLogin = loginRepository.findById(empId).get();
        if (empLogin != null) {
            if (passwordEncoder.matches(password, empLogin.getPassWord())) ;
            return empLogin;
        }
        return null;
    }

    @Override
    public Login forgetPassword(String email,Integer otpVal) {
//        int otpVal = 0;
        Login login = loginRepository.findByEmail(email);
        if (login != null) {
//            otpVal = new Random().nextInt(999999);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("anddie0904@gmail.com");
            message.setTo(email);
            message.setSubject("Eipulse員工驗證碼");
            message.setText("您的驗證碼：" + otpVal);
            javaMailSender.send(message);
            return login;
        }
        return null;
    }

    @Override
    public boolean newPassword(Login login,String newPassword) {
        login.setPassWord(passwordEncoder.encode(newPassword));
        loginRepository.save(login);
        return true;
    }

    //    @Override
//    public boolean forgetPassword(String email,Integer otpCheck,String newPassword) {
//        int otpVal=0;
//        Login login = loginRepository.findByEmail(email);
//        if ( login != null) {
//            otpVal = new Random().nextInt(999999);
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("anddie0904@gmail.com");
//            message.setTo(email);
//            message.setSubject("Eipulse員工驗證碼");
//            message.setText("您的驗證碼："+otpVal);
//            javaMailSender.send(message);
//        }
//        if(otpVal==otpCheck){
//            login.setPassWord(passwordEncoder.encode(newPassword));
//            loginRepository.save(login);
//            return true;
//        }
//        return false;
//    }
    @Override
    public String updatePassword(Integer empId, String oldPassword, String newPassword) {

        return null;
    }

    @Override
    public void logout(HttpSession httpSession) {
    }
}
