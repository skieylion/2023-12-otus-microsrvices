package com.example.auth.domain;

import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Data
public class UserRegistrationDto {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private Set<String> roles;
}
