package com.example.order.service.integration;

import com.example.order.domain.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(url = "${app.integration.billing-service.url}", name = "billing-service", value = "billing-service")
public interface BillingIntegrationService {
    @GetMapping("/account/by-user/{userId}")
    AccountDto getAccountByUser(@PathVariable(value = "userId") UUID userId);

    @PutMapping("/accounts/{accountId}")
    void setBalance(@PathVariable("accountId") UUID accountId, @RequestParam("type") String operationType, @RequestParam("quantity") BigDecimal quantity);
}
