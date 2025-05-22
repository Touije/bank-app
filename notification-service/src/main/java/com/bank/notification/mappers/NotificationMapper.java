package com.bank.notification.mappers;

import com.bank.notification.dto.response.NotificationResponse;
import com.bank.notification.entities.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    
    public NotificationResponse toResponse(Notification notification) {
        if (notification == null) return null;
        
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setClientId(notification.getClientId());
        response.setEmailClient(notification.getEmailClient());
        response.setTypeNotification(notification.getTypeNotification());
        response.setSujet(notification.getSujet());
        response.setContenu(notification.getContenu());
        response.setDateEnvoi(notification.getDateEnvoi());
        response.setEnvoye(notification.isEnvoye());
        response.setStatut(notification.getStatut());
        response.setMessageErreur(notification.getMessageErreur());
        
        return response;
    }

    public Notification toEntity(NotificationResponse response) {
        if (response == null) return null;
        
        Notification notification = new Notification();
        notification.setId(response.getId());
        notification.setClientId(response.getClientId());
        notification.setEmailClient(response.getEmailClient());
        notification.setTypeNotification(response.getTypeNotification());
        notification.setSujet(response.getSujet());
        notification.setContenu(response.getContenu());
        notification.setDateEnvoi(response.getDateEnvoi());
        notification.setEnvoye(response.isEnvoye());
        notification.setStatut(response.getStatut());
        notification.setMessageErreur(response.getMessageErreur());
        
        return notification;
    }
} 