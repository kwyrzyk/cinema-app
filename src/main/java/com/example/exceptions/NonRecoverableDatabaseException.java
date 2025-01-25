package com.example.exceptions;

public class NonRecoverableDatabaseException extends RuntimeException{
    
    public NonRecoverableDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
