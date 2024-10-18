package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Allergie;
import com.nuitriapp.equilibro.repository.AllergieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergieService {
    @Autowired
    private AllergieRepository allergieRepository;

    public List<Allergie> obtenirToutesLesAllergies() {
        return allergieRepository.findAll();
    }

    public Allergie ajouterAllergie(Allergie allergie) {
        return allergieRepository.save(allergie);
    }

    public Allergie obtenirAllergieParId(Long id) {
        return allergieRepository.findById(id).orElse(null);
    }

    public Allergie mettreAJourAllergie(Long id, Allergie allergie) {
        Allergie existante = allergieRepository.findById(id).orElse(null);
        if (existante != null) {
            existante.setNom(allergie.getNom());
            existante.setDescription(allergie.getDescription());
            return allergieRepository.save(existante);
        }
        return null;
    }

    public void supprimerAllergie(Long id) {
        allergieRepository.deleteById(id);
    }
}
