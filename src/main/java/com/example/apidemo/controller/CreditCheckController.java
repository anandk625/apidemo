package com.example.apidemo.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class CreditCheckController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/credit-score")
    @CircuitBreaker(name = "creditService", fallbackMethod = "creditFallback")
    public String getCreditScore(@RequestParam String ssn) {
        return restTemplate.getForObject("http://localhost:8089/credit-score?ssn=" + ssn, String.class);
    }

    public String creditFallback(String ssn, Throwable t) {
        return "Fallback: Credit service is currently unavailable for SSN: " + ssn;
    }
}
