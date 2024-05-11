package com.example.demo.filter;

import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BFilter implements ServiceFilter<User> {

    @Override
    public boolean supportFilter(User registryCreateDto) {
        return true;
    }

    @Override
    public void doFilter(User registryCreateDto) {
        System.out.println("BFilter");
    }

    public void doFilter(int x, String y) {
        System.out.println("BFilter x y");
    }
}
