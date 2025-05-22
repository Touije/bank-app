package com.bank.compte.service;

import com.bank.compte.dto.CompteDTO;
import com.bank.compte.dto.events.ClientCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CompteEventListener {
    private static final Logger log = LoggerFactory.getLogger(CompteEventListener.class);
    private final CompteService compteService;

    @KafkaListener(topics = "client-created", groupId = "compte-group")
    public void handleClientCreatedEvent(ClientCreatedEvent event) {
        log.info("Événement ClientCreated reçu pour le client: {}", event.getClientId());
        
        try {
            // Créer un compte courant par défaut
            String numeroCompteCourant = generateNumeroCompte(event.getClientId(), "COURANT");
            CompteDTO compteCourant = new CompteDTO();
            compteCourant.setNumeroCompte(numeroCompteCourant);
            compteCourant.setSolde(BigDecimal.ZERO);
            compteCourant.setTypeCompte("COURANT");
            compteCourant.setClientId(event.getClientId());
            compteCourant.setActif(true);
            compteCourant.setEmailClient(event.getEmail());
            
            compteService.createCompte(compteCourant);
            log.info("Compte courant créé pour le client: {}", event.getClientId());

            // Créer un compte épargne par défaut
            String numeroCompteEpargne = generateNumeroCompte(event.getClientId(), "EPARGNE");
            CompteDTO compteEpargne = new CompteDTO();
            compteEpargne.setNumeroCompte(numeroCompteEpargne);
            compteEpargne.setSolde(BigDecimal.ZERO);
            compteEpargne.setTypeCompte("EPARGNE");
            compteEpargne.setClientId(event.getClientId());
            compteEpargne.setActif(true);
            compteEpargne.setEmailClient(event.getEmail());
            
            compteService.createCompte(compteEpargne);
            log.info("Compte épargne créé pour le client: {}", event.getClientId());
            
        } catch (Exception e) {
            log.error("Erreur lors de la création des comptes pour le client {}: {}", 
                     event.getClientId(), e.getMessage());
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