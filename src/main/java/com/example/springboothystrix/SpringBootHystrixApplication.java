package com.example.springboothystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class SpringBootHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHystrixApplication.class, args);
	}
}
