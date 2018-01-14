package com.example.springboothystrix.controller;

import com.example.springboothystrix.controller.api.MessageResponse;
import com.example.springboothystrix.service.HystrixExceptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exception")
public class HystrixExceptionController {

    private final HystrixExceptionService hystrixExceptionService;

    public HystrixExceptionController(HystrixExceptionService hystrixExceptionService) {
        this.hystrixExceptionService = hystrixExceptionService;
    }

    @GetMapping("random-trigger-ignore-exception")
    public MessageResponse randomTriggerIgnoreThrownException() {
        String message = hystrixExceptionService.randomTriggerIgnoreThrownException();
        return new MessageResponse(message);
    }

    @GetMapping("random-trigger-hystrix-bad-request-exception")
    public MessageResponse randomlyTriggerHystrixBadRequestException() {
        String message = hystrixExceptionService.randomlyTriggerHystrixBadRequestException();
        return new MessageResponse(message);
    }
}
