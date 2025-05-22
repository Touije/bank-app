package com.bank.client.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreatedEvent {
    private Long clientId;
    private String nom;
    private String prenom;
    private String email;
} 