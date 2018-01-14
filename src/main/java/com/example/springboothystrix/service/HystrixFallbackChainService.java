package com.example.springboothystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class HystrixFallbackChainService {

    @HystrixCommand(fallbackMethod = "secondMethod")
    public String firstMethod() {
        throw new RuntimeException("firstMethod failed");
    }

    @HystrixCommand(fallbackMethod = "thirdMethod")
    public String secondMethod() {
        throw new RuntimeException("secondMethod failed");
    }

    @HystrixCommand(fallbackMethod = "lastMethod")
    public String thirdMethod() {
        throw new RuntimeException("thirdMethod failed");
    }

    public String lastMethod() {
        return "Last method message";
    }
}
