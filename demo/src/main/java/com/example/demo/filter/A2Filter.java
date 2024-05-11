package com.example.demo.filter;

import com.example.demo.domain.ExchangeCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class A2Filter implements ServiceFilter<ExchangeCreateDto> {

    @Override
    public boolean supportFilter(ExchangeCreateDto registryCreateDto) {
        return true;
    }

    @Override
    public void doFilter(ExchangeCreateDto registryCreateDto) {
        System.out.println("A2Filter");
    }
}
