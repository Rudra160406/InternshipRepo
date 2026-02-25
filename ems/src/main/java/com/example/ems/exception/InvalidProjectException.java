package com.example.ems.exception;

public class InvalidProjectException extends RuntimeException {
    public InvalidProjectException(String msg) {
        super(msg);
    }
}
