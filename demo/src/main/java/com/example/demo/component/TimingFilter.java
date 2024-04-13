package com.example.demo.component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Order(1)
public class TimingFilter implements Filter {

    private final MeterRegistry meterRegistry;

    public TimingFilter(MeterRegistry registry) {
        this.meterRegistry = registry;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Timer.Sample timer = Timer.start(meterRegistry);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            var httpRequest = (HttpServletRequest) servletRequest;
            timer.stop(Timer.builder("http.request.time")
                    .description("Time taken to process HTTP request")
                    .tags("method", httpRequest.getMethod(), "uri", httpRequest.getRequestURI())
                    .publishPercentiles(0.50, 0.95, 0.99, 1.0)
                    .publishPercentileHistogram()
                    .register(meterRegistry));
        }
    }
}
