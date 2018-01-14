package com.example.springboothystrix.controller;

import com.example.springboothystrix.controller.api.MessageResponse;
import com.example.springboothystrix.service.HystrixFallbackChainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fallback-chain")
public class HystrixFallbackChainController {

    private final HystrixFallbackChainService hystrixFallbackChainService;

    public HystrixFallbackChainController(HystrixFallbackChainService hystrixFallbackChainService) {
        this.hystrixFallbackChainService = hystrixFallbackChainService;
    }

    @GetMapping
    public MessageResponse fallbackChain() {
        String message = hystrixFallbackChainService.firstMethod();
        return new MessageResponse(message);
    }
}
