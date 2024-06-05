package com.example.billing.service;

import com.example.billing.domain.Account;
import com.example.billing.domain.OperationType;
import com.example.billing.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public String createAccount(String userId) {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setBalance(BigDecimal.ZERO);
        account.setUserId(UUID.fromString(userId));
        account = accountRepository.save(account);
        return account.getId().toString();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @SneakyThrows
    public void setBalance(String accountId, OperationType operationType, BigDecimal quantity) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("quantity can't be <= 0");
        }
        if (quantity.scale() > 4) {
            throw new BadRequestException("scale can't be more then 4");
        }
        Account account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow(EntityNotFoundException::new);
        switch (operationType) {
            case PAY_OFF -> account.setBalance(account.getBalance().add(quantity));
            case TAKE_OFF -> {
                if (account.getBalance().compareTo(quantity) < 0) {
                    throw new BadRequestException("The balance is less then " + quantity);
                }
                account.setBalance(account.getBalance().subtract(quantity));
            }
        }
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account getAccount(UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Account getAccountByUser(UUID userId) {
        return accountRepository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
    }
}

