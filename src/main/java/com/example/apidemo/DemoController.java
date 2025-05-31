package com.example.apidemo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/hello")
    @RateLimiter(name = "demoApiRateLimiter")
    @Retry(name = "demoApiRetry")
    @CircuitBreaker(name = "demoApiCB", fallbackMethod = "fallbackHello")
    @TimeLimiter(name = "demoApiTimeout")
    public CompletableFuture<String> hello(@RequestParam(defaultValue = "false") boolean fail) {
        logger.info("Received request for /hello?fail={}", fail);
        return CompletableFuture.supplyAsync(() -> {
            if (fail) {
                logger.warn("Simulating failure in /hello");
                throw new RuntimeException("Simulated failure!");
            }
            return "Hello, secure, observable, resilient world!";
        });
    }
}