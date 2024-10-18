package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Consultation;
import com.nuitriapp.equilibro.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    // Ajouter une nouvelle consultation
    @PostMapping("/ajouter")
    public ResponseEntity<Consultation> ajouterConsultation(@RequestBody Consultation consultation) {
        Consultation nouvelleConsultation = consultationService.creerConsultation(consultation);
        return ResponseEntity.ok(nouvelleConsultation);
    }

    // Obtenir toutes les consultations
    @GetMapping("/liste")
    public ResponseEntity<List<Consultation>> obtenirToutesLesConsultations() {
        List<Consultation> consultations = consultationService.obtenirToutesLesConsultations();
        return ResponseEntity.ok(consultations);
    }

    // Obtenir une consultation par ID
    @GetMapping("/obtenir/{id}")
    public ResponseEntity<Consultation> obtenirConsultationParId(@PathVariable Long id) {
        return consultationService.obtenirConsultationParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une consultation
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerConsultation(@PathVariable Long id) {
        consultationService.supprimerConsultation(id);
        return ResponseEntity.noContent().build();
    }

    // Obtenir le nombre total de consultations
    @GetMapping("/nombre-total")
    public ResponseEntity<Long> obtenirNombreTotalConsultations() {
        long totalConsultations = consultationService.compterTotalConsultations();
        return ResponseEntity.ok(totalConsultations);
    }

}