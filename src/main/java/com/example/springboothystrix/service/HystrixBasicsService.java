package com.example.springboothystrix.service;

import com.example.springboothystrix.exception.MyFallbackException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class HystrixBasicsService {

    private static final String MESSAGE = "Ok";
    private static final String FALLBACK_MESSAGE = "Fallback message";

    @HystrixCommand
    public String randomTrigger() {
        randomlyThrowException();
        return MESSAGE;
    }

    @HystrixCommand(fallbackMethod = "randomTriggerFallback")
    public String randomTriggerWithFallback() {
        randomlyThrowException();
        return MESSAGE;
    }

    @HystrixCommand(fallbackMethod = "randomTriggerFallbackException")
    public String randomTriggerWithFallbackException() {
        randomlyThrowException();
        return MESSAGE;
    }

    public String randomTriggerFallback() {
        return FALLBACK_MESSAGE;
    }

    public String randomTriggerFallbackException(Throwable t) {
        throw new MyFallbackException("some awesome error message explaining what went wrong", t);
    }

    private void randomlyThrowException() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("failure was triggered");
        }
    }

}
