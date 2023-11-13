package com.eipulse.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableScheduling //固定時間進行資料更新，須額外再要使用的方法上寫 @Scheduled(cron = "0 0 23 * * *")
@SpringBootApplication
public class TeamprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamprojectApplication.class, args);
		System.out.println(" EIPulse 後端啟動成功     \n" + 
				  " ______    _____  _____          _        \n"
				+ "|  ____|  |_   _||  __ \\        | |  \n"
				+ "| |__       | |  | |__) | _   _ | | ___  ___      \n"
				+ "|  __|      | |  |  ___/ | | | || |/ __|/  _ \\      \n"
				+ "| |____    _| |_ |  |    | |_| || |\\__  \\ |__/       \n"
				+ "|______|  |_____||_ |    \\ ____||_||___/ \\___|     \n");
	}
	

}
