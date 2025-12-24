package com.example.demo.componentTest;

import org.springframework.stereotype.Component;

@Component
public class UserPojo {
	
	int id;
	int age;
	String name;
	
	/* Here if @component is used then spring will create object using default constructor only.
	 * So if there is no default constructor then it will create a default constructor implicitly and create 
	 * object using that constructor only and store in the spring container. And if we define default constructor
	 * then spring will use that default constructor to create object and store in the spring container.
	 * As in my case I have defined default constructor so spring will use that to create object. and print the
	 * UserPojo object created from default constructor message in the console.
	 * 
	 * Console output:
	 * 
2025-12-24T19:15:47.257+05:30INFO 7400 --- [Annotation_practice] [           main] c.e.demo.AnnotationPracticeApplication   : Starting AnnotationPracticeApplication using Java 17.0.10 with PID 7400 (C:\Users\91985\Desktop\Test\SpringBoot\Annotation\Annotation_practice\target\classes started by 91985 in C:\Users\91985\Desktop\Test\SpringBoot\Annotation\Annotation_practice)
2025-12-24T19:15:47.261+05:30INFO 7400 --- [Annotation_practice] [           main] c.e.demo.AnnotationPracticeApplication   : No active profile set, falling back to 1 default profile: "default"
UserPojo object created from default constructor
2025-12-24T19:15:47.692+05:30INFO 7400 --- [Annotation_practice] [           main] c.e.demo.AnnotationPracticeApplication   : Started AnnotationPracticeApplication in 0.839 seconds (process running for 1.445)

	 * 
	 * 
	 * */
	
	public UserPojo() {
		super();
		System.out.println("UserPojo object created from default constructor");
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "UserPojo [id=" + id + ", age=" + age + ", name=" + name + "]";
	}
	
	
}
