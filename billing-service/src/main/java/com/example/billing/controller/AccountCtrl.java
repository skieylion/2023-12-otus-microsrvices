package com.example.billing.controller;

import com.example.billing.domain.Account;
import com.example.billing.domain.OperationType;
import com.example.billing.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountCtrl {
    private final AccountService accountService;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public String createAccount(@RequestParam("user_id") String userId) {
        return accountService.createAccount(userId);
    }

    @PutMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setBalance(@PathVariable("accountId") String accountId,
                           @RequestParam("type") OperationType operationType,
                           @RequestParam("quantity") BigDecimal quantity) {
        accountService.setBalance(accountId, operationType, quantity);
    }

    @GetMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable("accountId") UUID accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/account/by-user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountByUserId(@PathVariable("userId") UUID userId) {
        return accountService.getAccountByUser(userId);
    }
}
