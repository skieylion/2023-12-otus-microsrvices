package com.example.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Test endpoint", description = "Test operations")
public class HealthController {
    @GetMapping("/health/")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }
}
