package com.example.order.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountDto {
    private UUID id;
    private BigDecimal balance;
    private UUID userId;
}
