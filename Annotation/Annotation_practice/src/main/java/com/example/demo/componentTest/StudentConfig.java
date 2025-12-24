package com.example.demo.componentTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
	
	//StudentPojo studentPojo;
	
	@Bean
	public StudentPojo createStudentPojo() {
		System.out.println("StudentPojo object created from StudentConfig class");
		return new StudentPojo(1,22,"John");
		
	}
}
