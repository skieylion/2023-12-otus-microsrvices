package com.example.notification.domain;


import lombok.Data;

@Data
public class NotificationDto {
    private String code;
    private String title;
    private String message;
}
