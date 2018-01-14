package com.example.springboothystrix.exception;

public class MyFallbackException extends RuntimeException {

    public MyFallbackException(String message) {
        super(message);
    }

    public MyFallbackException(String message, Throwable cause) {
        super(message, cause);
    }
}
