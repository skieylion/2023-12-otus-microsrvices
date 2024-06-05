package com.example.order.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderDetailsDto {
    private UUID productId;
    private BigDecimal quantity;
}
