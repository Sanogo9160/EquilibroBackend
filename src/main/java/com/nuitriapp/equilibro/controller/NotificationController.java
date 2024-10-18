package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Notification;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.service.NotificationService;
import com.nuitriapp.equilibro.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UtilisateurService utilisateurService;
/*
    // Endpoint pour obtenir toutes les notifications
    @GetMapping("/toutes")
    public ResponseEntity<List<Notification>> obtenirToutesLesNotifications() {
        List<Notification> notifications = notificationService.obtenirToutesLesNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/envoyer")
    public ResponseEntity<Notification> envoyerNotification(@RequestBody Notification notification) {
        // Récupérer l'utilisateur authentifié à partir du SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUtilisateur = authentication.getName();  // Récupère l'email de l'utilisateur authentifié

        // Récupérer l'utilisateur à partir de son email
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.obtenirUtilisateurParEmail(emailUtilisateur);

        // Vérifier si l'utilisateur existe
        if (!optionalUtilisateur.isPresent()) {
            throw new RuntimeException("Utilisateur authentifié non trouvé.");
        }

        Utilisateur utilisateur = optionalUtilisateur.get();  // Déballer l'Optional

        // Associer l'utilisateur à la notification
        notification.setUtilisateur(utilisateur);

        // Envoyer la notification et la sauvegarder
        Notification nouvelleNotification = notificationService.envoyerNotification(notification);

        return ResponseEntity.ok(nouvelleNotification);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<Notification>> obtenirNotifications(@PathVariable Long utilisateurId) {
        return ResponseEntity.ok(notificationService.obtenirNotificationsParUtilisateur(utilisateurId));
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerNotification(@PathVariable Long id) {
        notificationService.supprimerNotification(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pour obtenir le nombre de notifications d'un utilisateur
    @GetMapping("/utilisateur/{utilisateurId}/nombre")
    public ResponseEntity<Long> obtenirNombreNotifications(@PathVariable Long utilisateurId) {
        Long nombreNotifications = notificationService.obtenirNombreDeNotifications(utilisateurId);
        return ResponseEntity.ok(nombreNotifications);
    }


 */
}