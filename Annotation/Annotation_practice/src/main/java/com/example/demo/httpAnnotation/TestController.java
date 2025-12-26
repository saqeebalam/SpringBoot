package com.example.demo.httpAnnotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This annotation indicates that this class is a Spring MVC controller.
@RequestMapping("/testCntrl") // This annotation maps HTTP requests to /test to this controller.
@ResponseBody
public class TestController {
	
	@GetMapping("/hello") // This annotation maps HTTP requests to /test/hello to this method.
	//@ResponseBody
	public String hello() {
		return "Hello, World! Responce Time: "+System.currentTimeMillis();
	}
	
	/*	
	 * Here in above class we have used @Controller annotation instead of @RestController.
	 * Then in the hello() method we are returning a String. and this String will be treated as the name of a view (like a JSP page) that should be rendered.
	 * So when a request is made to /testCntrl/hello, Spring MVC will look for a view named "Hello, World! Responce Time: <timestamp>" to render.
	 * And since there is no such view, it will result in an Whitelabel Error Page.
	 * To fix this issue we use @ResponseBody annotation on the hello() method to indicate that the return value should be used as the response body.
	 * And this @ResponseBody annotation can also be applied at the class level to apply it to all methods in the controller class and tell all methods to return response body will be web responce.
	 * And this @ResponseBody annotation can we write in method level or class level but not both.
	 * And this @ResponseBody annotation is already included in the @RestController annotation.
	 */

}
