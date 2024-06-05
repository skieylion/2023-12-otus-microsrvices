package com.example.order.service.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(url = "${app.integration.notification-service.url}", name = "notification-service", value = "notification-service")
public interface NotificationIntegrationService {
    @PostMapping("/notifications")
    void sendNotification(@RequestParam("code") String code, @RequestParam("user_id") UUID userId, @RequestParam("order_id") UUID orderId);
}
