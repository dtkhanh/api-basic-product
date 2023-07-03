package com.example.apibasicproduct.exception;

public class DuplicatedException extends RuntimeException {
    private String message;

    public DuplicatedException(String error, String name) {
        this.message = error + name;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
