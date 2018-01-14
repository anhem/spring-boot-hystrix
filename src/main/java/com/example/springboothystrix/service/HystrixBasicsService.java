package com.example.springboothystrix.service;

import com.example.springboothystrix.exception.MyFallbackException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HystrixBasicsService {

    private static final Logger log = LoggerFactory.getLogger(HystrixBasicsService.class);

    private static final String MESSAGE = "Ok";
    private static final String FALLBACK_MESSAGE = "Fallback message";

    /**
     * Circuit breaker closed:
     *      Returns message when everything is OK
     *      Returns RuntimeException upon failure
     * Circuit breaker open:
     *      Returns HystrixRuntimeException with the message:
     *      "alwaysTimeout short-circuited and fallback failed" because we have no fallback configured
     */
    @HystrixCommand
    public String randomTrigger() {
        randomlyThrowException();
        return MESSAGE;
    }

    /**
     * Circuit breaker closed:
     *      Returns message when everything is OK
     *      Returns fallback message upon failure
     * Circuit breaker open:
     *      Returns fallback message
     */
    @HystrixCommand(fallbackMethod = "randomTriggerFallback")
    public String randomTriggerWithFallback() {
        randomlyThrowException();
        return MESSAGE;
    }

    /**
     * Circuit breaker closed:
     *      Returns message when everything is OK
     *      Returns fallback message upon failure
     * Circuit breaker open:
     *      Returns HystrixRuntimeException containing a MyFallbackException that can contain whatever information we want to send back to the client
     *      This will however cause an error message to be logged:
     *      c.n.h.c.javanica.command.GenericCommand  : failed to process fallback is the method: 'randomTriggerFallbackException'.
     */
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
        if (Math.random() > 0.4) {
            log.warn("failure is being triggered");
            throw new RuntimeException("failure was triggered");
        }
    }

}
