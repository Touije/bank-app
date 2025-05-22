package com.bank.notification.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCreatedEvent {
    private String clientId;
    private String emailClient;
    private String typeCompte;
    private String numeroCompte;
    private String rib;
    private String codeCarte;
} 