package com.example.springboothystrix.controller;

import com.example.springboothystrix.controller.api.MessageResponse;
import com.example.springboothystrix.service.HystrixTimeoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("timeout")
public class HystrixTimeoutController {

    private final HystrixTimeoutService hystrixTimeoutService;

    public HystrixTimeoutController(HystrixTimeoutService hystrixTimeoutService) {
        this.hystrixTimeoutService = hystrixTimeoutService;
    }

    /**
     * Returns HystrixRuntimeException upon failure with the message:
     * alwaysTimeout timed-out and fallback failed..
     */
    @GetMapping("always-timeout")
    public MessageResponse alwaysTimeout() throws InterruptedException {
        String message = hystrixTimeoutService.alwaysTimeout();
        return new MessageResponse(message);
    }

    /**
     * Returns fallback message
     */
    @GetMapping("always-timeout-with-fallback")
    public MessageResponse alwaysTimeoutWithFallback() throws InterruptedException {
        String message = hystrixTimeoutService.alwaysTimeoutWithFallback();
        return new MessageResponse(message);
    }

    /**
     * Returns HystrixRuntimeException containing a MyFallbackException that can contain whatever information we want to send back to the client
     * This will however cause an error message to be logged:
     * c.n.h.c.javanica.command.GenericCommand  : failed to process fallback is the method: 'randomTriggerFallbackException'.
     */
    @GetMapping("always-timeout-with-fallback-exception")
    public MessageResponse alwaysTimeoutWithFallbackException() throws InterruptedException {
        String message = hystrixTimeoutService.alwaysTimeoutWithFallbackException();
        return new MessageResponse(message);
    }
}
