package com.example.notification.service;

import com.example.notification.domain.Notification;
import com.example.notification.domain.NotificationCode;
import com.example.notification.domain.NotificationDto;
import com.example.notification.domain.NotificationTemplate;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.repository.NotificationTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void sendNotification(NotificationCode code, String userId, String orderId) {
        NotificationTemplate notificationTemplate = notificationTemplateRepository.findByCode(code)
                .orElseThrow(EntityNotFoundException::new);
        Notification notification = new Notification();
        notification.setNotificationTemplateId(notificationTemplate.getId());
        notification.setId(UUID.randomUUID());
        notification.setUserId(UUID.fromString(userId));
        notification.setOrderId(UUID.fromString(orderId));
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationDto> findNotificationsByUserAndOrder(String userId, String orderId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndOrderId(UUID.fromString(userId), UUID.fromString(orderId));
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for (var notification : notifications) {
            NotificationDto dto = new NotificationDto();
            NotificationTemplate notificationTemplate = notificationTemplateRepository.findById(notification.getNotificationTemplateId())
                    .orElseThrow(EntityNotFoundException::new);
            dto.setCode(notificationTemplate.getCode().name());
            dto.setTitle(notificationTemplate.getTitle());
            dto.setMessage(notificationTemplate.getMessage());
            notificationDtoList.add(dto);
        }
        return notificationDtoList;
    }
}
