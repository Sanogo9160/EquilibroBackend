package com.nuitriapp.equilibro.service;


import com.nuitriapp.equilibro.model.ProgrammeFidelite;
import com.nuitriapp.equilibro.repository.ProgrammeFideliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgrammeFideliteService {

    @Autowired
    private ProgrammeFideliteRepository programmeFideliteRepository;

    // Lister tous les programmes de fidélité
    public List<ProgrammeFidelite> listerProgrammesFidelite() {
        return programmeFideliteRepository.findAll();
    }

    // Obtenir un programme de fidélité par ID
    public Optional<ProgrammeFidelite> obtenirProgrammeFideliteParId(Long id) {
        return programmeFideliteRepository.findById(id);
    }

    // Créer un nouveau programme de fidélité
    public ProgrammeFidelite creerProgrammeFidelite(ProgrammeFidelite programmeFidelite) {
        return programmeFideliteRepository.save(programmeFidelite);
    }

    // Ajouter des points à un programme de fidélité existant
    public ProgrammeFidelite ajouterPoints(Long id, Integer pointsAjoutes) {
        ProgrammeFidelite programme = programmeFideliteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programme de fidélité non trouvé"));

        // Logique d'ajout des points dans le service
        if (pointsAjoutes != null && pointsAjoutes > 0) {
            programme.setPoints(programme.getPoints() + pointsAjoutes);
        } else {
            throw new IllegalArgumentException("Les points ajoutés doivent être positifs.");
        }

        return programmeFideliteRepository.save(programme); // Sauvegarder les modifications
    }

    // Utiliser (ou rédemption) des points dans un programme de fidélité existant
    public ProgrammeFidelite utiliserPoints(Long id, Integer pointsUtilises) {
        ProgrammeFidelite programme = programmeFideliteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programme de fidélité non trouvé"));

        // Logique de rédemption dans le service
        if (programme.getPoints() >= pointsUtilises && pointsUtilises > 0) {
            programme.setPoints(programme.getPoints() - pointsUtilises);
        } else {
            throw new IllegalArgumentException("Points insuffisants ou points utilisés invalides.");
        }

        return programmeFideliteRepository.save(programme); // Sauvegarder les modifications
    }

    // Supprimer un programme de fidélité
    public void supprimerProgrammeFidelite(Long id) {
        programmeFideliteRepository.deleteById(id);
    }
}