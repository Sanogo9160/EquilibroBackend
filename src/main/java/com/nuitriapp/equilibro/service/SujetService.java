package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Sujet;
import com.nuitriapp.equilibro.repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SujetService {

    @Autowired
    private SujetRepository sujetRepository;

    // Récupérer tous les sujets
    public List<Sujet> obtenirTousLesSujets() {
        return sujetRepository.findAll();
    }

    // Récupérer un sujet par ID
    public Optional<Sujet> obtenirSujetParId(Long id) {
        return sujetRepository.findById(id);
    }

    // Créer un nouveau sujet
    public Sujet creerSujet(Sujet sujet) {
        return sujetRepository.save(sujet);
    }

    // Supprimer un sujet par ID
    public void supprimerSujet(Long id) {
        sujetRepository.deleteById(id);
    }

}
