package com.example.auth.domain;

import lombok.Data;

import java.util.Set;

@Data
public class UserEditDto {
    private String fullName;
    private String email;
    private String phone;
    private Set<String> roles;
}
