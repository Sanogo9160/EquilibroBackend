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

    public void bookConsultation(ConsultationDTO consultationDTO) {
        Optional<Dieteticien> dieteticien = dieteticienRepository.findById(consultationDTO.getDieteticienId());
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(consultationDTO.getUtilisateurId());

        if (dieteticien.isPresent() && utilisateur.isPresent()) {
            Consultation consultation = new Consultation();
            consultation.setDieteticien(dieteticien.get());
            consultation.setUtilisateur(utilisateur.get());
            consultation.setDateConsultation(consultationDTO.getDateConsultation());

            consultationRepository.save(consultation);
        } else {
            throw new RuntimeException("Dieteticien ou Utilisateur non trouvé");
        }
    }

    public void confirmerConsultation(Long consultationId) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée"));

        consultation.setEstConfirmee(true);
        consultationRepository.save(consultation);
    }

}
