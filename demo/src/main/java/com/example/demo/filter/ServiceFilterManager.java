package com.example.demo.filter;

import java.util.List;
import java.util.Optional;

public abstract class ServiceFilterManager<T> {
    private final List<ServiceFilter<T>> serviceFilters;

    protected ServiceFilterManager(List<ServiceFilter<T>> serviceFilters) {
        this.serviceFilters = serviceFilters;
    }

    public void doFilter(T entity) {
        serviceFilters.stream().filter(serviceFilter -> serviceFilter.supportFilter(entity))
                .forEach(serviceFilter -> serviceFilter.doFilter(entity));
    }

    public <F> Optional<F> findByType(Class<F> clazz) {
        return serviceFilters.stream().filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();
    }
}
