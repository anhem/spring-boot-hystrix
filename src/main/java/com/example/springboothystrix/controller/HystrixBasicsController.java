package com.example.springboothystrix.controller;

import com.example.springboothystrix.controller.api.ErrorResponse;
import com.example.springboothystrix.controller.api.HystrixErrorResponse;
import com.example.springboothystrix.controller.api.MessageResponse;
import com.example.springboothystrix.exception.MyFallbackException;
import com.example.springboothystrix.service.HystrixBasicsService;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping("hystrix")
public class HystrixBasicsController {

    private static final Logger log = LoggerFactory.getLogger(HystrixBasicsController.class);

    private final HystrixBasicsService hystrixBasicsService;

    public HystrixBasicsController(HystrixBasicsService hystrixBasicsService) {
        this.hystrixBasicsService = hystrixBasicsService;
    }

    /**
     * Circuit breaker closed:
     *      Returns message when everything is OK
     *      Returns RuntimeException upon failure
     * Circuit breaker open:
     *      Returns HystrixRuntimeException with the message:
     *      "randomTrigger short-circuited and fallback failed" because we have no fallback configured
     */
    @GetMapping("random-trigger")
    public MessageResponse randomTrigger() {
        String message = hystrixBasicsService.randomTrigger();
        return new MessageResponse(message);
    }

    /**
     * Circuit breaker closed:
     *      Returns message when everything is OK
     *      Returns fallback message upon failure
     * Circuit breaker open:
     *      Returns fallback message
     */
    @GetMapping("random-trigger-with-fallback")
    public MessageResponse randomTriggerWithFallback() {
        String message = hystrixBasicsService.randomTriggerWithFallback();
        return new MessageResponse(message);
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
    @GetMapping("random-trigger-with-fallback-exception")
    public MessageResponse randomTriggerWithFallbackException() {
        String message = hystrixBasicsService.randomTriggerWithFallbackException();
        return new MessageResponse(message);
    }

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
