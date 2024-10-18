package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.ObjectifSante;
import com.nuitriapp.equilibro.repository.ObjectifSanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ObjectifSanteService {
    @Autowired
    private ObjectifSanteRepository objectifSanteRepository;

    public List<ObjectifSante> obtenirTousLesObjectifs() {
        return objectifSanteRepository.findAll();
    }

    public ObjectifSante ajouterObjectif(ObjectifSante objectifSante) {
        return objectifSanteRepository.save(objectifSante);
    }

    public ObjectifSante obtenirObjectifParId(Long id) {
        return objectifSanteRepository.findById(id).orElse(null);
    }

    public ObjectifSante mettreAJourObjectif(Long id, ObjectifSante objectifSante) {
        ObjectifSante existant = objectifSanteRepository.findById(id).orElse(null);
        if (existant != null) {
            existant.setNom(objectifSante.getNom());
            existant.setDescription(objectifSante.getDescription());
            return objectifSanteRepository.save(existant);
        }
        return null;
    }

    public void supprimerObjectif(Long id) {
        objectifSanteRepository.deleteById(id);
    }
}
