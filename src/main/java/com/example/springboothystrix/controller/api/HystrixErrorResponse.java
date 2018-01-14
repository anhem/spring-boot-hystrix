package com.example.springboothystrix.controller.api;

import com.netflix.hystrix.exception.HystrixRuntimeException;

public class HystrixErrorResponse extends ErrorResponse {

    private final HystrixRuntimeException.FailureType failureType;
    private final String fallbackExceptionMessage;

    public HystrixErrorResponse(String error, String stacktrace, int httpCode, HystrixRuntimeException.FailureType failureType, String fallbackExceptionMessage) {
        super(error, stacktrace, httpCode);
        this.failureType = failureType;
        this.fallbackExceptionMessage = fallbackExceptionMessage;
    }

    public HystrixRuntimeException.FailureType getFailureType() {
        return failureType;
    }

    public String getFallbackExceptionMessage() {
        return fallbackExceptionMessage;
    }
}
