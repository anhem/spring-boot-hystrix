package com.example.springboothystrix.service;

import com.example.springboothystrix.exception.MyFallbackException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HystrixTimeoutService {

    private static final Logger log = LoggerFactory.getLogger(HystrixTimeoutService.class);

    private static final String MESSAGE = "Ok";
    private static final String FALLBACK_MESSAGE = "Fallback message";
    private static final String HYSTRIX_TIMEOUT = "1000";
    private static final int SLOW_OPERATION_THREAD_SLEEP = 3000;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = HYSTRIX_TIMEOUT)}
    )
    public String alwaysTimeout() throws InterruptedException {
        tooSlowOperation();
        return MESSAGE;
    }

    @HystrixCommand(fallbackMethod = "alwaysTimeoutFallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = HYSTRIX_TIMEOUT)})
    public String alwaysTimeoutWithFallback() throws InterruptedException {
        tooSlowOperation();
        return MESSAGE;
    }

    @HystrixCommand(fallbackMethod = "alwaysTimeoutFallbackException",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = HYSTRIX_TIMEOUT)})
    public String alwaysTimeoutWithFallbackException() throws InterruptedException {
        tooSlowOperation();
        return MESSAGE;
    }

    public String alwaysTimeoutFallback() {
        return FALLBACK_MESSAGE;
    }

    public String alwaysTimeoutFallbackException(Throwable t) {
        throw new MyFallbackException("some awesome error message explaining what went wrong", t);
    }

    private void tooSlowOperation() throws InterruptedException {
        Thread.sleep(SLOW_OPERATION_THREAD_SLEEP);
        log.warn("This will never happen when our Hystrix timeout is lower than thread sleep. Hystrix will (by default) abort this thread on timeout");
        throw new RuntimeException("failure was triggered");
    }

}
