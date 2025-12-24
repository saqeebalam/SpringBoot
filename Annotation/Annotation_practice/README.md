# Annotation_practice

Small Spring Boot example project demonstrating component scanning and a simple POJO bean.

Project structure
- src/main/java/com/example/demo: main application class
- src/main/java/com/example/demo/componentTest: contains ComponentPract and UserPojo

Key files
- AnnotationPracticeApplication.java — Spring Boot application entry point
- componentTest/UserPojo.java — a simple POJO annotated with @Component (parameterized constructor)

Prerequisites
- JDK 11+ (project built with Maven)
- Maven 3.6+

Build and run
- Build: mvn clean package
- Run from IDE: run AnnotationPracticeApplication
- Run jar: java -jar target/Annotation_practice-<version>.jar

Notes
- The UserPojo class currently defines a parameterized constructor and is annotated with @Component. If you rely on Spring to create the bean using a default constructor, either add a no-arg constructor or register the bean with configuration.
- application.properties is present under src/main/resources for configuration.

Testing
- Unit tests (if any) can be run with: mvn test

License
- No license specified.

# UserPojo and Spring Bean Instantiation (Explanation)

This README explains the behavior described in the comment inside
src/main/java/com/example/demo/componentTest/UserPojo.java. It covers
how spring create object in IOC container. Here we use @Component to create a object of userpojo it is no parameterized or default constructor to create object.
This obbjec is create when out appliccation is starting. If we defines beans scope then as per scope it crate object.

# StudentPojo and Spring Bean Instantiation (Explanation)

This README explains the behavior described in the comment inside
src/main/java/com/example/demo/componentTest/StudentPojo.java. It covers
why Spring fails to create a @Component bean when only a parameterized
constructor is present, the error you will see, and the recommended
solutions.

Project locations
- Student POJO: src/main/java/com/example/demo/componentTest/StudentPojo.java
- Configuration example: src/main/java/com/example/demo/componentTest/StudentConfig.java
- Service example (uses the bean): src/main/java/com/example/demo/componentTest/StudentService.java

Problem summary
- If you annotate a class with @Component and it has only a parameterized
  constructor (for example, StudentPojo(int id, int age, String name)),
  Spring will try to create an instance by using that constructor.
- For each constructor parameter Spring will attempt to resolve a bean of
  the matching type from the application context. Primitive types (int,
  double, etc.) and plain String values are not beans and therefore Spring
  cannot supply them automatically.

Typical runtime error
When Spring fails to resolve a constructor parameter you will see an error
similar to:

  Parameter 0 of constructor in com.example.demo.componentTest.StudentPojo
  required a bean of type 'int' that could not be found.

This prevents the application context from starting.

Why this happens
- Spring prefers constructors for instantiation and will use the available
  constructor(s) to create the bean. If a constructor has parameters,
  Spring treats them as dependencies to inject.
- Without information to satisfy primitive or non-bean parameters, Spring
  cannot construct the object.

Solutions and recommendations
1) Add a default (no-arg) constructor
   - Best practice for simple POJOs you want Spring to manage without
     constructor injection.
   - Spring will use the no-arg constructor to instantiate the bean and
     then call setters to populate fields if needed.
   - Example: public StudentPojo() { /* ... */ }

2) Provide a @Bean factory method in a @Configuration class (StudentConfig)
   - Define a method that constructs and returns the StudentPojo with
     values you choose. Spring will call that method and register the
     returned object as a bean.
   - Useful when you want to inject specific values at configuration time.
   - Example (in StudentConfig):
     @Bean
     public StudentPojo studentPojo() {
         return new StudentPojo(1, 20, "Alice");
     }

3) Use @Value or @ConfigurationProperties for parameter injection
   - If constructor parameters should come from properties, use @Value or
     bind with @ConfigurationProperties. This requires the parameters to be
     non-primitive wrappers (Integer) or properly configured.

4) Use @Lazy (not recommended here)
   - @Lazy delays bean creation until first use, but it does not solve the
     underlying problem of missing dependency values. It can hide the
     error until runtime and is not recommended as the primary solution.

Notes about autowiring
- If you register the bean via @Component and fix the constructor issue
  (add default constructor or use @Autowired constructors with resolvable
  dependencies), the bean can be injected into other components (for
  example StudentService) with @Autowired.

How to reproduce and test
- Uncomment @Component on StudentPojo and run the application without
  adding a no-arg constructor or bean definition. The context will fail to
  start with the error shown above.
- Fix by adding one of the recommended solutions and re-run the
  application; it should start successfully.

Conclusion
- For simple Spring-managed POJOs, provide a no-arg constructor or register
  the object explicitly via a @Bean method. Reserve constructor-based
  dependency injection for genuine bean dependencies that Spring can
  resolve.