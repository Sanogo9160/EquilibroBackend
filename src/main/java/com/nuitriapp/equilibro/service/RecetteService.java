package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Recette;
import com.nuitriapp.equilibro.repository.RecetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecetteService {
    @Autowired
    private final RecetteRepository recetteRepository;
    private final EdamamService edamamService;

    public RecetteService(RecetteRepository recetteRepository, EdamamService edamamService) {
        this.recetteRepository = recetteRepository;
        this.edamamService = edamamService;
    }

    public Recette getRecette(String label) {
        Optional<Recette> recetteOptional = recetteRepository.findByLabel(label);
        if (recetteOptional.isPresent()) {
            return recetteOptional.get();
        }
        Recette nouvelleRecette = edamamService.fetchRecetteFromAPI(label);
        recetteRepository.save(nouvelleRecette);
        return nouvelleRecette;
    }

    public void saveRecette(Recette recette) {
        recetteRepository.save(recette);
    }


}
