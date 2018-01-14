package com.example.springboothystrix.controller;

import com.example.springboothystrix.controller.api.ErrorResponse;
import com.example.springboothystrix.controller.api.HystrixErrorResponse;
import com.example.springboothystrix.exception.MyFallbackException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(value = MyFallbackException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMyFallbackException(MyFallbackException e, WebRequest webRequest) {
        log.warn("MyFallbackException on request: {}", webRequest, e);
        return new ErrorResponse(e.getMessage(), getStacktrace(e), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = HystrixRuntimeException.class)
    public HystrixErrorResponse handleHystrixRuntimeException(HystrixRuntimeException e, WebRequest webRequest) {
        log.error("HystrixRuntimeException on request: {}", webRequest, e);
        return new HystrixErrorResponse(e.getMessage(), getStacktrace(e), HttpStatus.BAD_REQUEST.value(), e.getFailureType(), e.getFallbackException().getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException e, WebRequest webRequest) {
        log.error("Exception on request: {}", webRequest, e);
        return new ErrorResponse(e.getMessage(), getStacktrace(e), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private String getStacktrace(Exception e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        return writer.toString();
    }
}
