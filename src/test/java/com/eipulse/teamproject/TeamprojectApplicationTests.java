package com.eipulse.teamproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class TeamprojectApplicationTests {

	@SuppressWarnings("unused")
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
	}

}
