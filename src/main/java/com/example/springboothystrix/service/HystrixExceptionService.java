package com.example.springboothystrix.service;

import com.example.springboothystrix.exception.MyFallbackException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HystrixExceptionService {

    private static final Logger log = LoggerFactory.getLogger(HystrixExceptionService.class);

    private static final String MESSAGE = "Ok";
    private static final String FALLBACK_MESSAGE = "Fallback message";

    /**
     * Circuit breaker closed:
     *      Returns message when everything is OK
     *      Returns IllegalArgumentException upon failure
     * Circuit breaker open:
     *      never occurs. The circuit never opens because the only type of exception that is thrown is configured to be ignored
     */
    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = IllegalArgumentException.class)
    public String randomTriggerIgnoreThrownException() {
        randomlyThrowIllegalArgumentException();
        return MESSAGE;
    }

    /**
     * Circuit breaker closed:
     *      Returns message when everything is OK
     *      Returns HystrixBadRequestException upon failure
     * Circuit breaker open:
     *      never occurs. The circuit never opens because the only type of exception we throw is ignored by Hystrix
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public String randomlyTriggerHystrixBadRequestException() {
        randomlyThrowHystrixBadRequestException();
        return MESSAGE;
    }

    public String fallback() {
        log.warn("This will never happen when we throw an exception that is ignored, or we throw HystrixBadRequestException");
        return FALLBACK_MESSAGE;
    }

    public String randomTriggerFallbackException(Throwable t) {
        throw new MyFallbackException("some awesome error message explaining what went wrong", t);
    }

    private void randomlyThrowIllegalArgumentException() {
        if (Math.random() > 0.4) {
            log.warn("IllegalArgumentException is being triggered");
            throw new IllegalArgumentException("some illegal argument error");
        }
    }

    private void randomlyThrowHystrixBadRequestException() {
        if (Math.random() > 0.4) {
            log.warn("HystrixBadRequestException is being triggered");
            throw new HystrixBadRequestException("some bad request error");
        }
    }

}
