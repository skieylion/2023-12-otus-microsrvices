package com.example.demo.filter;

import com.example.demo.domain.ExchangeCreateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AFilterManager extends ServiceFilterManager<ExchangeCreateDto> {
    public AFilterManager(List<ServiceFilter<ExchangeCreateDto>> serviceFilters) {
        super(serviceFilters);
    }
}
