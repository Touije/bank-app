package com.bank.notification.repositories;

import com.bank.notification.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByClientId(String clientId);
    List<Notification> findByEnvoye(boolean envoye);
    List<Notification> findByTypeNotificationAndEnvoye(String typeNotification, boolean envoye);
} 