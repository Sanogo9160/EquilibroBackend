package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Maladie;
import com.nuitriapp.equilibro.repository.MaladieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaladieService {

    @Autowired
    private MaladieRepository maladieRepository;

    public List<Maladie> obtenirToutesLesMaladies() {
        return maladieRepository.findAll();
    }

    public Maladie ajouterMaladie(Maladie maladie) {
        return maladieRepository.save(maladie);
    }

    public Maladie obtenirMaladieParId(Long id) {
        return maladieRepository.findById(id).orElse(null);
    }

    public Maladie mettreAJourMaladie(Long id, Maladie maladie) {
        Maladie existante = maladieRepository.findById(id).orElse(null);
        if (existante != null) {
            existante.setNom(maladie.getNom());
            existante.setDescription(maladie.getDescription());
            return maladieRepository.save(existante);
        }
        return null;
    }

    public void supprimerMaladie(Long id) {
        maladieRepository.deleteById(id);
    }

}
