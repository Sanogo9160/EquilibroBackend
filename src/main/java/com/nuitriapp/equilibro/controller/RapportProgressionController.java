package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.RapportProgression;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.service.RapportProgressionService;
import com.nuitriapp.equilibro.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rapports")
public class RapportProgressionController {

    @Autowired
    private RapportProgressionService rapportProgressionService;

    // Récupérer tous les rapports de progression
    @GetMapping("/liste")
    public ResponseEntity<List<RapportProgression>> obtenirTousLesRapports() {
        List<RapportProgression> rapports = rapportProgressionService.obtenirTousLesRapports();
        return new ResponseEntity<>(rapports, HttpStatus.OK);
    }

    // Récupérer un rapport de progression par son ID
    @GetMapping("/obtenir/{id}")
    public ResponseEntity<RapportProgression> obtenirRapportParId(@PathVariable Long id) {
        Optional<RapportProgression> rapport = rapportProgressionService.obtenirRapportParId(id);
        return rapport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau rapport de progression
    @PostMapping("/generer")
    public ResponseEntity<RapportProgression> creerRapport(@RequestBody RapportProgression rapport) {
        RapportProgression nouveauRapport = rapportProgressionService.creerRapport(rapport);
        return new ResponseEntity<>(nouveauRapport, HttpStatus.CREATED);
    }

    // Supprimer un rapport de progression
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerRapport(@PathVariable Long id) {
        rapportProgressionService.supprimerRapport(id);
        return ResponseEntity.noContent().build();
    }
}