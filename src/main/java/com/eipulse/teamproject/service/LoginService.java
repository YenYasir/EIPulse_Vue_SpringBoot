package com.eipulse.teamproject.service;

import com.eipulse.teamproject.config.SecurityConfig;
import com.eipulse.teamproject.entity.Login;
import com.eipulse.teamproject.repository.LoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class LoginService {
    private LoginRepository loginRepository;
    private JavaMailSender javaMailSender;
    private SecurityConfig securityConfig;


    @Autowired
    public LoginService(LoginRepository loginRepository, JavaMailSender javaMailSender,SecurityConfig securityConfig) {
        this.loginRepository = loginRepository;
        this.javaMailSender = javaMailSender;
        this.securityConfig = securityConfig;
    }


    public Login checkLogin(Integer empId, String password) {
        Optional<Login> optionalLogin = loginRepository.findById(empId);

        if (optionalLogin.isPresent()) {

            Login empLogin = optionalLogin.get();

            if (password != null && empLogin.getPassword() != null) {
                if (securityConfig.passwordEncoder().matches(password, empLogin.getPassword()) ||password.equals(empLogin.getPassword())) {
                    return empLogin;
                }
            }
        }
        throw new UnsupportedOperationException("登入失敗");
    }


    public Login forgetPassword(String email,Integer otpVal) {
        Optional<Login> optional = loginRepository.findByEmail(email);
        if (optional.isPresent()) {
            Login login = optional.get();
//進階版Thread
            CompletableFuture.runAsync(()->{
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("anddie0904@gmail.com");
                message.setTo(email);
                message.setSubject("Eipulse員工驗證碼");
                message.setText("您的驗證碼：" + otpVal);
                javaMailSender.send(message);
            }).exceptionally(ex->{
                throw new UnsupportedOperationException("mail發送失敗");
            });

            return login;
        }
        throw new UnsupportedOperationException("無此email");
    }

    public boolean newPassword(Login login,String newPassword) {
        login.setPassword(securityConfig.passwordEncoder().encode(newPassword));
        loginRepository.save(login);
        return true;
    }

    public String updatePassword(Integer empId, String oldPassword, String newPassword) {

        return null;
    }

    public void logout(HttpSession httpSession) {
    }
}
