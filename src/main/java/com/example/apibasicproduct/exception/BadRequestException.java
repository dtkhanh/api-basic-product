package com.example.apibasicproduct.exception;

public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException(String badRequest) {
        this.message = badRequest;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
