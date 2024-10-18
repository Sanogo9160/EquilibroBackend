package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Notification;
import com.nuitriapp.equilibro.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    // Méthode pour obtenir le nombre de notifications d'un utilisateur
    public Long obtenirNombreDeNotifications(Long utilisateurId) {
        return notificationRepository.countByUtilisateurId(utilisateurId);
    }

    // Méthode pour obtenir toutes les notifications
    public List<Notification> obtenirToutesLesNotifications() {
        return notificationRepository.findAll();
    }

    public Notification envoyerNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> obtenirNotificationsParUtilisateur(Long utilisateurId) {
        return notificationRepository.findByUtilisateurId(utilisateurId);
    }

    public void supprimerNotification(Long id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            notificationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Notification non trouvée avec l'id : " + id);
        }
    }

}
