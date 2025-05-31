package com.example.apidemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/")
    public String home() {
        return "Welcome to the API Demo! Try /api/hello (HTTP Basic Auth required).";
    }
}