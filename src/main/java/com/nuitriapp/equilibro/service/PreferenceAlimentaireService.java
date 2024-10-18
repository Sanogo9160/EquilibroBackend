package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.PreferenceAlimentaire;
import com.nuitriapp.equilibro.repository.PreferenceAlimentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PreferenceAlimentaireService {

    @Autowired
    private PreferenceAlimentaireRepository preferenceAlimentaireRepository;

    public List<PreferenceAlimentaire> obtenirToutesLesPreferences() {
        return preferenceAlimentaireRepository.findAll();
    }

    public PreferenceAlimentaire obtenirPreferenceParId(Long id) {
        return preferenceAlimentaireRepository.findById(id).orElse(null);
    }

    public PreferenceAlimentaire ajouterPreference(PreferenceAlimentaire preferenceAlimentaire) {
        return preferenceAlimentaireRepository.save(preferenceAlimentaire);
    }

    public PreferenceAlimentaire mettreAJourPreference(Long id, PreferenceAlimentaire preferenceAlimentaire) {
        PreferenceAlimentaire existante = preferenceAlimentaireRepository.findById(id).orElse(null);
        if (existante != null) {
            existante.setNom(preferenceAlimentaire.getNom());
            existante.setDescription(preferenceAlimentaire.getDescription());
            return preferenceAlimentaireRepository.save(existante);
        }
        return null;
    }

    public void supprimerPreference(Long id) {
        preferenceAlimentaireRepository.deleteById(id);
    }
}
