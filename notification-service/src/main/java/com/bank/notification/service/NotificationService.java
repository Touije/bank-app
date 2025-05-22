package com.bank.notification.service;

import com.bank.compte.dto.events.CompteCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    
    private final JavaMailSender mailSender;

    @Autowired
    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "compte-created", groupId = "notification-group")
    public void handleCompteCreated(CompteCreatedEvent event) {
        log.info("Réception de l'événement de création de compte pour le client: {}", event.getClientId());
        
        String message = String.format(
            "Bonjour,\n\n" +
            "Votre compte %s a été créé avec succès.\n\n" +
            "Détails du compte :\n" +
            "Numéro de compte : %s\n" +
            "RIB : %s\n" +
            "Code de carte : %s\n\n" +
            "Cordialement,\n" +
            "Votre Banque",
            event.getTypeCompte(),
            event.getNumeroCompte(),
            event.getRib(),
            event.getCodeCarte()
        );

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(event.getEmailClient());
        email.setSubject("Création de votre compte bancaire");
        email.setText(message);
        
        try {
            mailSender.send(email);
            log.info("Email envoyé avec succès à {}", event.getEmailClient());
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email à {}: {}", event.getEmailClient(), e.getMessage());
            // Ici, vous pourriez implémenter une logique de retry ou de dead letter queue
        }
    }
} 