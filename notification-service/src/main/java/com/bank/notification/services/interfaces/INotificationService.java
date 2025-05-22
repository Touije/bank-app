package com.bank.notification.services.interfaces;

import com.bank.notification.dto.request.CreateNotificationRequest;
import com.bank.notification.dto.response.NotificationResponse;
import com.bank.notification.dto.events.CompteCreatedEvent;
import java.util.List;

public interface INotificationService {
    NotificationResponse createNotification(CreateNotificationRequest request);
    List<NotificationResponse> getAllNotifications();
    List<NotificationResponse> getNotificationsByClientId(String clientId);
    List<NotificationResponse> getNotificationsByStatus(boolean envoye);
    void handleCompteCreated(CompteCreatedEvent event);
} 