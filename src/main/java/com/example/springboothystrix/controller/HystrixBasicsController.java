package com.example.springboothystrix.controller;

import com.example.springboothystrix.controller.api.MessageResponse;
import com.example.springboothystrix.service.HystrixBasicsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("basics")
public class HystrixBasicsController {

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
     *      "alwaysTimeout short-circuited and fallback failed" because we have no fallback configured
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
}
