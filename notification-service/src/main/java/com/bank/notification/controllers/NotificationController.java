package com.bank.notification.controllers;

import com.bank.notification.services.interfaces.INotificationService;
import com.bank.notification.dto.request.CreateNotificationRequest;
import com.bank.notification.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody CreateNotificationRequest request) {
        return ResponseEntity.ok(notificationService.createNotification(request));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByClientId(
            @PathVariable String clientId) {
        return ResponseEntity.ok(notificationService.getNotificationsByClientId(clientId));
    }

    @GetMapping("/status/{envoye}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByStatus(
            @PathVariable boolean envoye) {
        return ResponseEntity.ok(notificationService.getNotificationsByStatus(envoye));
    }
} 