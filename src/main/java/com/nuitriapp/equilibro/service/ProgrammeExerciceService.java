package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.ProgrammeExercice;
import com.nuitriapp.equilibro.repository.ProgrammeExerciceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgrammeExerciceService {
    @Autowired
    private ProgrammeExerciceRepository programmeExerciceRepository;

    // Lister tous les programmes d'exercice
    public List<ProgrammeExercice> listerProgrammesExercices() {
        return programmeExerciceRepository.findAll();
    }

    // Obtenir un programme d'exercice par ID
    public Optional<ProgrammeExercice> obtenirProgrammeExerciceParId(Long id) {
        return programmeExerciceRepository.findById(id);
    }

    // Créer un nouveau programme d'exercice
    public ProgrammeExercice creerProgrammeExercice(ProgrammeExercice programmeExercice) {
        return programmeExerciceRepository.save(programmeExercice);
    }

    // Supprimer un programme d'exercice par ID
    public void supprimerProgrammeExercice(Long id) {
        programmeExerciceRepository.deleteById(id);
    }

    // Modifier un programme d'exercice existant
    public ProgrammeExercice modifierProgrammeExercice(Long id, ProgrammeExercice programmeExerciceModifie) {
        Optional<ProgrammeExercice> programmeExistant = programmeExerciceRepository.findById(id);

        if (programmeExistant.isPresent()) {
            ProgrammeExercice programme = programmeExistant.get();
            programme.setNom(programmeExerciceModifie.getNom());
            programme.setDescription(programmeExerciceModifie.getDescription());
            programme.setNiveau(programmeExerciceModifie.getNiveau());
            programme.setUtilisateur(programmeExerciceModifie.getUtilisateur());
            return programmeExerciceRepository.save(programme);
        } else {
            throw new RuntimeException("Programme d'exercice non trouvé pour l'ID : " + id);
        }
    }
}