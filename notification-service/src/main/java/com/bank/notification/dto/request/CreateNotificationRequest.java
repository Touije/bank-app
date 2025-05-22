package com.bank.notification.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateNotificationRequest {
    @NotBlank(message = "Le clientId est obligatoire")
    private String clientId;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String emailClient;

    @NotBlank(message = "Le type de notification est obligatoire")
    private String typeNotification;

    @NotBlank(message = "Le sujet est obligatoire")
    private String sujet;

    @NotBlank(message = "Le contenu est obligatoire")
    private String contenu;
} 