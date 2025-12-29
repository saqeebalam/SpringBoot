package com.movie.exception;



public class ActorErrorResponse { // Changed name to avoid conflicts
    private int status;
    private String message;
    private long timeStamp;
    
    // 1. ADD THIS: The "No-Argument" Constructor
    public ActorErrorResponse() {
    }

    // IMPORTANT: Make sure you have a public constructor
    public ActorErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    // Getters and Setters are required for Jackson to convert this to JSON
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public long getTimeStamp() { return timeStamp; }
    public void setTimeStamp(long timeStamp) { this.timeStamp = timeStamp; }
}