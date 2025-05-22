package com.bank.notification.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private String clientId;
    private String emailClient;
    private String typeNotification;
    private String sujet;
    private String contenu;
    private LocalDateTime dateEnvoi;
    private boolean envoye;
    private String statut;
    private String messageErreur;
} 