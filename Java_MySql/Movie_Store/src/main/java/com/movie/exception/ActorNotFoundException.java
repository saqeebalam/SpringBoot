package com.movie.exception;

//Custom exception for "Not Found" scenarios
public class ActorNotFoundException extends RuntimeException {
 /**
	 * 
	 */
	private static final long serialVersionUID = 3250395336099413874L;

 public ActorNotFoundException(String message) {
     super(message);
 }
}
