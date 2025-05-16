package com.example.studentstay.orm.util.exceptions;

public class TransactionException extends OrmException {
    public TransactionException(String message) {
        super(message);
    }
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}