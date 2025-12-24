package com.example.demo.componentTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentService {
	
	@Autowired
    private StudentPojo studentPojo;

    public void print() {
        System.out.println(studentPojo);
    }

}
