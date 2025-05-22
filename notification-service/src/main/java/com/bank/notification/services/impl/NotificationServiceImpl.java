package com.bank.notification.services.impl;

import com.bank.notification.services.interfaces.INotificationService;
import com.bank.notification.exceptions.NotificationException;
import com.bank.notification.entities.Notification;
import com.bank.notification.repositories.NotificationRepository;
import com.bank.notification.mappers.NotificationMapper;
import com.bank.notification.dto.request.CreateNotificationRequest;
import com.bank.notification.dto.response.NotificationResponse;
import com.bank.notification.dto.events.CompteCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        try {
            Notification notification = new Notification();
            notification.setClientId(request.getClientId());
            notification.setEmailClient(request.getEmailClient());
            notification.setTypeNotification(request.getTypeNotification());
            notification.setSujet(request.getSujet());
            notification.setContenu(request.getContenu());
            notification.setDateEnvoi(LocalDateTime.now());
            notification.setEnvoye(false);

            sendEmail(notification);
            notification = notificationRepository.save(notification);
            
            return notificationMapper.toResponse(notification);
        } catch (Exception e) {
            throw new NotificationException("Erreur lors de la création de la notification", e);
        }
    }

    @Override
    @KafkaListener(topics = "compte-created", groupId = "notification-group")
    @Transactional
    public void handleCompteCreated(CompteCreatedEvent event) {
        log.info("Réception de l'événement de création de compte pour le client: {}", event.getClientId());
        
        CreateNotificationRequest request = new CreateNotificationRequest();
        request.setClientId(event.getClientId());
        request.setEmailClient(event.getEmailClient());
        request.setTypeNotification("EMAIL");
        request.setSujet("Création de votre compte bancaire");
        request.setContenu(formatCompteCreatedMessage(event));

        createNotification(request);
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getNotificationsByClientId(String clientId) {
        return notificationRepository.findByClientId(clientId).stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getNotificationsByStatus(boolean envoye) {
        return notificationRepository.findByEnvoye(envoye).stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void sendEmail(Notification notification) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(notification.getEmailClient());
            email.setSubject(notification.getSujet());
            email.setText(notification.getContenu());
            
            mailSender.send(email);
            
            notification.setEnvoye(true);
            notification.setStatut("SUCCESS");
            log.info("Email envoyé avec succès à {}", notification.getEmailClient());
        } catch (Exception e) {
            notification.setStatut("FAILED");
            notification.setMessageErreur(e.getMessage());
            log.error("Erreur lors de l'envoi de l'email à {}: {}", 
                     notification.getEmailClient(), e.getMessage());
            throw new NotificationException("Erreur lors de l'envoi de l'email", e);
        }
    }

    private String formatCompteCreatedMessage(CompteCreatedEvent event) {
        return String.format(
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
    }
} 