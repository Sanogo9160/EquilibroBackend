package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Notification;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void envoyerNotification(Utilisateur utilisateur, String message) {
        Notification notification = new Notification();
        notification.setUtilisateur(utilisateur);
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<Notification> obtenirNotificationPourrUtilisateur(Long utilisateurId) {
        return notificationRepository.findByUtilisateur_Id(utilisateurId);
    }



}
