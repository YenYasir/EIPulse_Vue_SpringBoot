package com.eipulse.teamproject;

import com.eipulse.teamproject.repository.EmpRepository;
import com.eipulse.teamproject.service.EmpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
class TeamprojectApplicationTests {

	@Autowired
	private EmpService empService;
	@Test
	@Transactional
	void contextLoads() {
	}

}
