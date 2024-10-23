package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.dto.ConsultationDTO;
import com.nuitriapp.equilibro.model.Consultation;
import com.nuitriapp.equilibro.model.Dieteticien;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.repository.ConsultationRepository;
import com.nuitriapp.equilibro.repository.DieteticienRepository;
import com.nuitriapp.equilibro.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DieteticienRepository dieteticienRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private NotificationService notificationService;

    public void bookConsultation(ConsultationDTO consultationDTO) {
        // Récupérer le diététicien et l'utilisateur
        Dieteticien dieteticien = getDieteticienById(consultationDTO.getDieteticienId());
        Utilisateur utilisateur = getUtilisateurById(consultationDTO.getUtilisateurId());

        // Créer et sauvegarder la consultation
        Consultation consultation = new Consultation();
        consultation.setDieteticien(dieteticien);
        consultation.setUtilisateur(utilisateur);
        consultation.setDateConsultation(consultationDTO.getDateConsultation());
        consultation.setMotif(consultationDTO.getMotif());
        consultation.setEstConfirmee(false);

        consultationRepository.save(consultation);

        // Envoyer notification au diététicien
        notificationService.envoyerNotification(dieteticien, "Nouvelle réservation de consultation reçue.");
    }

    public void confirmerConsultation(Long id) {
        Consultation consultation = getConsultationById(id);
        consultation.setEstConfirmee(true);
        consultationRepository.save(consultation);

        // Envoyer notification à l'utilisateur
        notificationService.envoyerNotification(consultation.getUtilisateur(), "Votre réservation de consultation a été confirmée.");
    }

    private Dieteticien getDieteticienById(Long id) {
        return dieteticienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dieteticien non trouvé avec l'ID: " + id));
    }

    private Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
    }

    private Consultation getConsultationById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée avec l'ID: " + id));
    }

}
