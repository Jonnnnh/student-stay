package com.example.studentstay.orm.util.exceptions;

public class OrmException extends RuntimeException {
  public OrmException(String message) {
    super(message);
  }
  public OrmException(String message, Throwable cause) {
    super(message, cause);
  }
}