package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.ProgrammeExercice;
import com.nuitriapp.equilibro.service.ProgrammeExerciceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/programmes-exercice")
public class ProgrammeExerciceController {

    @Autowired
    private ProgrammeExerciceService programmeExerciceService;

    // Lister tous les programmes d'exercice
    @GetMapping("/liste")
    public ResponseEntity<List<ProgrammeExercice>> listerProgrammesExercices() {
        List<ProgrammeExercice> programmes = programmeExerciceService.listerProgrammesExercices();
        return new ResponseEntity<>(programmes, HttpStatus.OK);
    }

    // Obtenir un programme d'exercice par ID
    @GetMapping("/obtenir/{id}")
    public ResponseEntity<ProgrammeExercice> obtenirProgrammeExerciceParId(@PathVariable Long id) {
        Optional<ProgrammeExercice> programmeExercice = programmeExerciceService.obtenirProgrammeExerciceParId(id);
        return programmeExercice.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau programme d'exercice
    @PostMapping("/creer")
    public ResponseEntity<ProgrammeExercice> creerProgrammeExercice(@RequestBody ProgrammeExercice programmeExercice) {
        ProgrammeExercice nouveauProgramme = programmeExerciceService.creerProgrammeExercice(programmeExercice);
        return new ResponseEntity<>(nouveauProgramme, HttpStatus.CREATED);
    }

    // Supprimer un programme d'exercice par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerProgrammeExercice(@PathVariable Long id) {
        programmeExerciceService.supprimerProgrammeExercice(id);
        return ResponseEntity.noContent().build();
    }

    // Modifier un programme d'exercice existant
    @PutMapping("/modifier/{id}")
    public ResponseEntity<ProgrammeExercice> modifierProgrammeExercice(@PathVariable Long id, @RequestBody ProgrammeExercice programmeExerciceModifie) {
        try {
            ProgrammeExercice programmeExercice = programmeExerciceService.modifierProgrammeExercice(id, programmeExerciceModifie);
            return new ResponseEntity<>(programmeExercice, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}