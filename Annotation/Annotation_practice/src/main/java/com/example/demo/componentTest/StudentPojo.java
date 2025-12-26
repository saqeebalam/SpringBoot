package com.example.demo.componentTest;


//@Component
public class StudentPojo {

	int id;
	int age;
	String name;
	
	
	/*
	 * But here if @component is used then spring will not use default constructor to create object. Sprint will use parameterized
	 * constructor to create object then there is an issue occur because this parameterized constructor have 3 parameters so spring will not know
	 * what values to pass for these parameters. So spring will throw an error like below:
	 * 

Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2025-12-24T19:20:29.130+05:30ERROR 13448 --- [Annotation_practice] [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in com.example.demo.componentTest.StudentPojo required a bean of type 'int' that could not be found.


Action:

Consider defining a bean of type 'int' in your configuration.
	 * 
	 * To solve this issue we have to option use @Lazy annotation but this is not a good practice.Because it will create object only when it is required.
	 * And when required it may create issue if the dependencies are not satisfied at that time.
	 * 
	 * Best way to define a default constructor along with parameterized constructor. So spring will use default constructor to create object.
	 * and other way is to use @Bean annotation in a configuration class to create object with default or require value . Then spring will use that method to create object and store in the spring container.
	 * and when we have to use we just have to Autowire that object.
	 * Example of @Bean annotation is given in the StudentConfig class. and StudentService class shows how to autowire and use that object.
	 * Note: we have to remove @component annotation from this class to avoid confusion. here we remove @Component annotation from StudentPojo class.
	 * 
	 * */
	public StudentPojo(int id, int age, String name) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		System.out.println("StudentPojo object created from parameterized constructor");
	}
	
//	public StudentPojo() {
//		super();
//		System.out.println("StudentPojo object created from default constructor");
//	}
	
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
