package com.example.demo.filter;

public interface ServiceFilter<T> {
    boolean supportFilter(T entity);

    void doFilter(T entity);
}
