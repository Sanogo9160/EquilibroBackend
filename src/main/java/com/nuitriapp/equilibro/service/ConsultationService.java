package com.nuitriapp.equilibro.service;


import com.nuitriapp.equilibro.model.Consultation;
import com.nuitriapp.equilibro.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    // Obtenir toutes les consultations
    public List<Consultation> obtenirToutesLesConsultations() {
        return consultationRepository.findAll();
    }

    // Obtenir une consultation par son ID
    public Optional<Consultation> obtenirConsultationParId(Long id) {
        return consultationRepository.findById(id);
    }

    // Créer une nouvelle consultation
    public Consultation creerConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    // Supprimer une consultation par son ID
    public void supprimerConsultation(Long id) {
        consultationRepository.deleteById(id);
    }

    // Obtenir le nombre total de consultations
    public long compterTotalConsultations() {
        return consultationRepository.count();
    }

}
