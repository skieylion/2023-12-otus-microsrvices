package com.example.order.domain;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderCreationRequest {
    private UUID userId;
    private List<OrderDetailsDto> products;
}
