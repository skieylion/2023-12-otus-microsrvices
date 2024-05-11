package com.example.demo.filter;

import com.example.demo.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BFilterManager extends ServiceFilterManager<User> {
    public BFilterManager(List<ServiceFilter<User>> serviceFilters) {
        super(serviceFilters);
    }
}
