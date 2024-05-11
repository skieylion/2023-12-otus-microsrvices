package com.example.demo.filter;

import com.example.demo.domain.ExchangeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1)
@Service
@RequiredArgsConstructor
public class AFilter implements ServiceFilter<ExchangeCreateDto> {

    @Override
    public boolean supportFilter(ExchangeCreateDto registryCreateDto) {
        return true;
    }

    @Override
    public void doFilter(ExchangeCreateDto registryCreateDto) {
        System.out.println("AFilter");
    }
}
