package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.RapportProgression;
import com.nuitriapp.equilibro.repository.RapportProgressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RapportProgressionService {

    @Autowired
    private RapportProgressionRepository rapportProgressionRepository;

    // Récupérer tous les rapports de progression
    public List<RapportProgression> obtenirTousLesRapports() {
        return rapportProgressionRepository.findAll();
    }

    // Récupérer un rapport par son ID
    public Optional<RapportProgression> obtenirRapportParId(Long id) {
        return rapportProgressionRepository.findById(id);
    }

    // Créer un nouveau rapport de progression
    public RapportProgression creerRapport(RapportProgression rapport) {
        return rapportProgressionRepository.save(rapport);
    }

    // Supprimer un rapport par son ID
    public void supprimerRapport(Long id) {
        rapportProgressionRepository.deleteById(id);
    }

}