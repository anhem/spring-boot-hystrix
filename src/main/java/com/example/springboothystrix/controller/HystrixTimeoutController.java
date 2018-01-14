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

    @GetMapping("always-timeout")
    public MessageResponse alwaysTimeout() throws InterruptedException {
        String message = hystrixTimeoutService.alwaysTimeout();
        return new MessageResponse(message);
    }

    @GetMapping("always-timeout-with-fallback")
    public MessageResponse alwaysTimeoutWithFallback() throws InterruptedException {
        String message = hystrixTimeoutService.alwaysTimeoutWithFallback();
        return new MessageResponse(message);
    }

    @GetMapping("always-timeout-with-fallback-exception")
    public MessageResponse alwaysTimeoutWithFallbackException() throws InterruptedException {
        String message = hystrixTimeoutService.alwaysTimeoutWithFallbackException();
        return new MessageResponse(message);
    }
}
