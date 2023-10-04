package com.eipulse.teamproject.serviceimp;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eipulse.teamproject.entitys.Login;
import com.eipulse.teamproject.repository.LoginRepository;
import com.eipulse.teamproject.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginServiceImp implements LoginService {
	private LoginRepository loginRepository;
	private PasswordEncoder passwordEncoder;
	private JavaMailSender javaMailSender;

	@Autowired
	public LoginServiceImp(LoginRepository loginRepository, PasswordEncoder passwordEncoder,
			JavaMailSender javaMailSender) {
		this.loginRepository = loginRepository;
		this.passwordEncoder = passwordEncoder;
		this.javaMailSender = javaMailSender;
	}

	@Override
	public Login checkLogin(Integer empId, String password) {
		Optional<Login> optionalLogin = loginRepository.findById(empId);

		if (optionalLogin.isPresent()) {

			Login empLogin = optionalLogin.get();

			if (password != null && empLogin.getPassword() != null) {
//              
				if (password.equals(empLogin.getPassword())) {
					return empLogin;
				}
			}
		}
		throw new UnsupportedOperationException("登入失敗");
	}

	@Override
	public Login forgetPassword(String email, Integer otpVal) {
		Optional<Login> optional = loginRepository.findByEmail(email);
		if (optional.isPresent()) {
			Login login = optional.get();
//進階版Thread
			CompletableFuture.runAsync(() -> {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("anddie0904@gmail.com");
				message.setTo(email);
				message.setSubject("Eipulse員工驗證碼");
				message.setText("您的驗證碼：" + otpVal);
				javaMailSender.send(message);
			}).exceptionally(ex -> {
				throw new UnsupportedOperationException("mail發送失敗");
			});

//            使用Thead讓mail執行速度加快
//            new Thread(()->{
//                SimpleMailMessage message = new SimpleMailMessage();
//                message.setFrom("anddie0904@gmail.com");
//                message.setTo(email);
//                message.setSubject("Eipulse員工驗證碼");
//                message.setText("您的驗證碼：" + otpVal);
//                javaMailSender.send(message);
//            }).start();

			return login;
		}
		throw new UnsupportedOperationException("無此email");
	}

	@Override
	public boolean newPassword(Login login, String newPassword) {
		login.setPassword(passwordEncoder.encode(newPassword));
		loginRepository.save(login);
		return true;
	}

	@Override
	public String updatePassword(Integer empId, String oldPassword, String newPassword) {

		return null;
	}

	@Override
	public void logout(HttpSession httpSession) {
	}
}
