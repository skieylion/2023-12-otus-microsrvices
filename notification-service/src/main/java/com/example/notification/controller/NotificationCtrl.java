package com.example.notification.controller;

import com.example.notification.domain.NotificationCode;
import com.example.notification.domain.NotificationDto;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationCtrl {
    private final NotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendNotification(@RequestParam("code") NotificationCode code, @RequestParam("user_id") String userId, @RequestParam("order_id") String orderId) {
        notificationService.sendNotification(code, userId, orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationDto> getNotifications(@RequestParam("user_id") String userId, @RequestParam("order_id") String orderId) {
        return notificationService.findNotificationsByUserAndOrder(userId, orderId);
    }
}
