package com.eipulse.teamproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
=======
import org.springframework.mail.SimpleMailMessage;
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class TeamprojectApplicationTests {

<<<<<<< HEAD
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
=======
	@Autowired
	private JavaMailSender javaMailSender;
	@Test
	void contextLoads() {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("anddie0904@gmail.com");
		message.setTo("d0981843347@gmail.com");
		message.setSubject("測試中");
		message.setText("這是一封測試郵件");
		javaMailSender.send(message);
>>>>>>> 1ca9295fe3eb08b4237b64000ca99a668a54be01
	}

}
