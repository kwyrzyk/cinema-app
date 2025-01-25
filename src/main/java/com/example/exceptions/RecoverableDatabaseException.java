package com.example.exceptions;

public class RecoverableDatabaseException extends RuntimeException{
 
    public RecoverableDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
