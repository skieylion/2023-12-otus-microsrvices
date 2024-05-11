package com.example.demo.service;

import com.example.demo.domain.ExchangeCreateDto;
import com.example.demo.domain.User;
import com.example.demo.filter.AFilterManager;
import com.example.demo.filter.BFilter;
import com.example.demo.filter.BFilterManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonService {
    private final AFilterManager aFilterManager;
    private final BFilterManager bFilterManager;

    public void run() {
        aFilterManager.doFilter(new ExchangeCreateDto("as", "sasd"));
        bFilterManager.doFilter(new User());
        bFilterManager.findByType(BFilter.class).ifPresent(f -> f.doFilter(1, "ad"));
    }

    public void run2() {
        ExchangeCreateDto createDto = new ExchangeCreateDto("ss", "asd3");
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("xxx", createDto);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        context.setVariable("objectMapper", mapper);
        SpelExpressionParser parser = new SpelExpressionParser();

        Expression expression = parser.parseExpression("#objectMapper.writeValueAsString(#xxx)");


        Object obj = expression.getValue(context);
        System.out.println(obj);
    }
}

