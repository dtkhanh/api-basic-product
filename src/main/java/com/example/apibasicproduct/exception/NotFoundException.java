package com.example.apibasicproduct.exception;


public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String message, Long id) {
        this.message = message + id.toString();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
