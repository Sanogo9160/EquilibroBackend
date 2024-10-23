package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.dto.NotificationRequestDTO;
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

    @GetMapping("/{utilisateurId}")
    public List<Notification> getNotifications(@PathVariable Long utilisateurId) {
        return notificationService.obtenirNotificationPourrUtilisateur(utilisateurId);
    }

    @PostMapping
    public ResponseEntity<String> envoyerNotification(@RequestBody NotificationRequestDTO request) {
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(request.getUtilisateurId());

            notificationService.envoyerNotification(utilisateur, request.getMessage());
            return ResponseEntity.ok("Notification envoyée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de la notification.");
        }
    }


}