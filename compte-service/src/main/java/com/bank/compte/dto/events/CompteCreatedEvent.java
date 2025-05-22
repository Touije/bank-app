package com.bank.compte.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCreatedEvent {
    private Long clientId;
    private String emailClient;
    private String numeroCompte;
    private String rib;
    private String codeCarte;
    private String typeCompte;
} 