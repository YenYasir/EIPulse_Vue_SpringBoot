package com.eipulse.teamproject;

import com.eipulse.teamproject.entitys.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;

@SpringBootTest
class TeamprojectApplicationTests {

	@Autowired
	private JavaMailSender javaMailSender;
	@Test
	void contextLoads() {

//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("anddie0904@gmail.com");
//		message.setTo("d0981843347@gmail.com");
//		message.setSubject("測試中");
//		message.setText("這是一封測試郵件");
//		javaMailSender.send(message);
		LocalDate time = LocalDate.of(2022,10,01);
//		int month =time.get();
//		String password = String.format("%02d", time.getMonthValue())+String.format("%02d",time.getDayOfMonth());
//		System.out.println(password);
	}

}
