package com.example.order.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class OrderCreationResponse {
    private UUID orderId;
    private UUID userId;
    private BigDecimal totalPrice;
    private boolean paidFor;
}
