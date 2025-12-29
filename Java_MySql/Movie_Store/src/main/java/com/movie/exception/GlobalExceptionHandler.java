package com.movie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle our custom "Actor Not Found" exception
    @ExceptionHandler(ActorNotFoundException.class)
    public ResponseEntity<ActorErrorResponse> handleException(ActorNotFoundException exc) {
        
        ActorErrorResponse error = new ActorErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Handle Type Mismatch (e.g., passing "abc" as an ID instead of a number)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ActorErrorResponse> handleException(MethodArgumentTypeMismatchException exc) {
        
        ActorErrorResponse error = new ActorErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid parameter: " + exc.getName() + " should be of type " + exc.getRequiredType().getSimpleName(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. Catch-all for any other generic exceptions (The Safety Net)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ActorErrorResponse> handleException(Exception exc) {
        
        ActorErrorResponse error = new ActorErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred: " + exc.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}