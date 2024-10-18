package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Administrateur;
import com.nuitriapp.equilibro.repository.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministrateurService {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    public Administrateur modifierAdministrateur(Long id, Administrateur administrateurDetails) {
        return administrateurRepository.findById(id).map(administrateur -> {
            administrateur.setNom(administrateurDetails.getNom());
            administrateur.setEmail(administrateurDetails.getEmail());
            // Autres modifications spécifiques à l'administrateur
            return administrateurRepository.save(administrateur);
        }).orElse(null);
    }
}

