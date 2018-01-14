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

    @GetMapping("random-trigger")
    public MessageResponse randomTrigger() {
        String message = hystrixBasicsService.randomTrigger();
        return new MessageResponse(message);
    }

    @GetMapping("random-trigger-with-fallback")
    public MessageResponse randomTriggerWithFallback() {
        String message = hystrixBasicsService.randomTriggerWithFallback();
        return new MessageResponse(message);
    }

    @GetMapping("random-trigger-with-fallback-exception")
    public MessageResponse randomTriggerWithFallbackException() {
        String message = hystrixBasicsService.randomTriggerWithFallbackException();
        return new MessageResponse(message);
    }
}
