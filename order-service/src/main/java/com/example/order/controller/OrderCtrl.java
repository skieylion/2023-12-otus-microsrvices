package com.example.order.controller;

import com.example.order.domain.OrderCreationRequest;
import com.example.order.domain.OrderCreationResponse;
import com.example.order.service.OrderCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class OrderCtrl {
    private final OrderCreatorService orderCreatorService;

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreationResponse createAccount(@RequestBody OrderCreationRequest orderCreationRequest) {
        return orderCreatorService.createOrder(orderCreationRequest);
    }
}
