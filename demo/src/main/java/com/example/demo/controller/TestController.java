package com.example.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@Tag(name = "Test endpoint", description = "Test operations")
public class TestController {
    @GetMapping("/health/")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }

    @GetMapping("/test")
    public Map<String, String> testGetMethod(
            @RequestParam("errorProbability") int errorProbability,
            @RequestParam("sleepStartTime") int startTime,
            @RequestParam("sleepEndTime") int endTime) {
        return doSomething(errorProbability, startTime, endTime);
    }

    @PostMapping("/test")
    public Map<String, String> testPostMethod(
            @RequestParam("errorProbability") int errorProbability,
            @RequestParam("sleepStartTime") int startTime,
            @RequestParam("sleepEndTime") int endTime) {
        return doSomething(errorProbability, startTime, endTime);
    }

    @SneakyThrows
    private Map<String, String> doSomething(int errorProbability, int startTime, int endTime) {
        int time = new Random().nextInt(startTime, endTime);
        Thread.sleep(time);
        if (new Random().nextInt(100) > errorProbability) {
            return Map.of("status", "OK");
        }
        throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
