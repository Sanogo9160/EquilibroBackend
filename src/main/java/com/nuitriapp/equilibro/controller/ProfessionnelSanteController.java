package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.ProfessionnelSante;
import com.nuitriapp.equilibro.service.ProfessionnelSanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professionnels-sante")
public class ProfessionnelSanteController {
    @Autowired
    private ProfessionnelSanteService professionnelSanteService;

    // Lister tous les professionnels de santé
    @GetMapping
    public ResponseEntity<List<ProfessionnelSante>> listerProfessionnelsSante() {
        List<ProfessionnelSante> professionnels = professionnelSanteService.listerProfessionnelsSante();
        return new ResponseEntity<>(professionnels, HttpStatus.OK);
    }

    // Obtenir un professionnel de santé par ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfessionnelSante> obtenirProfessionnelSanteParId(@PathVariable Long id) {
        Optional<ProfessionnelSante> professionnelSante = professionnelSanteService.obtenirProfessionnelSanteParId(id);
        return professionnelSante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau professionnel de santé
    @PostMapping("/creer")
    public ResponseEntity<ProfessionnelSante> creerProfessionnelSante(@RequestBody ProfessionnelSante professionnelSante) {
        ProfessionnelSante nouveauProfessionnel = professionnelSanteService.creerProfessionnelSante(professionnelSante);
        return new ResponseEntity<>(nouveauProfessionnel, HttpStatus.CREATED);
    }

    // Supprimer un professionnel de santé par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerProfessionnelSante(@PathVariable Long id) {
        professionnelSanteService.supprimerProfessionnelSante(id);
        return ResponseEntity.noContent().build();
    }

    // Modifier un professionnel de santé existant
    @PutMapping("/modifier/{id}")
    public ResponseEntity<ProfessionnelSante> modifierProfessionnelSante(@PathVariable Long id, @RequestBody ProfessionnelSante professionnelSanteModifie) {
        try {
            ProfessionnelSante professionnelSante = professionnelSanteService.modifierProfessionnelSante(id, professionnelSanteModifie);
            return new ResponseEntity<>(professionnelSante, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}