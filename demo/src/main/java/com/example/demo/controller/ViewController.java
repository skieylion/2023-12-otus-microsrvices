package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {
    @GetMapping("/view-controller/test")
    public ModelAndView test(ModelAndView modelAndView, HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
