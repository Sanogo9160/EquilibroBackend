package com.nuitriapp.equilibro.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RappelService {

    @Scheduled(cron = "0 0 12 * * ?")  // Rappel tous les jours à midi
    public void envoyerRappelRepas() {
        // Logique pour récupérer les utilisateurs et envoyer un email/push
    }
}
