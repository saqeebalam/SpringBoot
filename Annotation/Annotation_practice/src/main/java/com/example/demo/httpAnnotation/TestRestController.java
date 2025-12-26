package com.example.demo.httpAnnotation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This annotation indicates that this class is a RESTful controller.
@RequestMapping("/test") // This annotation maps HTTP requests to /test to this controller.
public class TestRestController {
	
	String name ="";
	
	@GetMapping("/hello") // This annotation maps HTTP requests to /test/hello to this method.
	public String hello() {
		return "Hello, World! "+name+ " Responce Time: "+System.currentTimeMillis();
	}
	
	@PostMapping("/setname/{name}") // This annotation maps HTTP POST requests to /test/setname/{name} to this method.
	public String setName(@PathVariable String name) {
		this.name = name;
		return "Name set to: " + name;
	}
	
	@PutMapping("/updatename/{name}") // This annotation maps HTTP PUT requests to /test/updatename/{name} to this method.
	public String updateName(@PathVariable String name) {
		this.name = name;
		return "Name updated to: " + name;
	}
	
	@DeleteMapping("/deletename") // This annotation maps HTTP DELETE requests to /test/deletename to this method.
	public String deleteName() {
		this.name = "";
		return "Name deleted";
	}
	
	@PatchMapping("/patchname/{name}") // This annotation maps HTTP PATCH requests to /test/patchname/{name} to this method.
	public String patchName(@PathVariable String name) {
		this.name += name;
		return "Name patched to: " + name;
	}
}
