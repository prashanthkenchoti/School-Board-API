package com.School.sba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//used to schedule the jobs OR automating some tasks
public class SchoolBoardApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolBoardApiApplication.class, args);
	}

}
