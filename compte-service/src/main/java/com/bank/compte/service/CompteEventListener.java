package com.bank.compte.service;

import com.bank.compte.dto.CompteDTO;
import com.bank.compte.dto.events.ClientCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompteEventListener {
    private final CompteService compteService;

    @KafkaListener(topics = "client-created", groupId = "compte-group")
    public void handleClientCreatedEvent(ClientCreatedEvent event) {
        log.info("Événement ClientCreated reçu pour le client: {}", event.getClientId());
        
        try {
            // Créer un compte courant par défaut
            CompteDTO compteCourant = new CompteDTO();
            compteCourant.setClientId(event.getClientId());
            compteCourant.setNumeroCompte(generateNumeroCompte(event.getClientId(), "COURANT"));
            compteCourant.setTypeCompte("COURANT");
            compteCourant.setSolde(BigDecimal.ZERO);
            compteCourant.setActif(true);
            
            compteService.createCompte(compteCourant);
            log.info("Compte courant créé pour le client: {}", event.getClientId());

            // Créer un compte épargne par défaut
            CompteDTO compteEpargne = new CompteDTO();
            compteEpargne.setClientId(event.getClientId());
            compteEpargne.setNumeroCompte(generateNumeroCompte(event.getClientId(), "EPARGNE"));
            compteEpargne.setTypeCompte("EPARGNE");
            compteEpargne.setSolde(BigDecimal.ZERO);
            compteEpargne.setActif(true);
            
            compteService.createCompte(compteEpargne);
            log.info("Compte épargne créé pour le client: {}", event.getClientId());
            
        } catch (Exception e) {
            log.error("Erreur lors de la création des comptes pour le client {}: {}", 
                     event.getClientId(), e.getMessage());
            // Ici, vous pourriez implémenter une logique de retry ou de dead letter queue
        }
    }

    private String generateNumeroCompte(Long clientId, String type) {
        // Format: FR76 XXXX XXXX XXXX XXXX XXXX XXX
        // XXXX = 4 chiffres aléatoires
        // XXX = 3 derniers chiffres du clientId
        String clientIdStr = String.format("%03d", clientId % 1000);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return String.format("FR76%s%s%s", random, random, clientIdStr);
    }
} 