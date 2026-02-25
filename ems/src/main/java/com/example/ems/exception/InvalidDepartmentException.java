package com.example.ems.exception;

public class InvalidDepartmentException extends RuntimeException {
    public InvalidDepartmentException(String msg) {
        super(msg);
    }
}
