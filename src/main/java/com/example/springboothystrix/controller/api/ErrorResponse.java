package com.example.springboothystrix.controller.api;

public class ErrorResponse {

    private final String error;
    private final String stacktrace;
    private final int httpCode;

    public ErrorResponse(String error, String stacktrace, int httpCode) {
        this.error = error;
        this.stacktrace = stacktrace;
        this.httpCode = httpCode;
    }

    public String getError() {
        return error;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
