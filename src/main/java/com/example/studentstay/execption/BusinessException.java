package com.example.studentstay.execption;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
