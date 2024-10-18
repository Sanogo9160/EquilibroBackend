package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Sujet;
import com.nuitriapp.equilibro.service.SujetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sujets")
public class SujetController {

    @Autowired
    private SujetService sujetService;

    // Récupérer tous les sujets
    @GetMapping("/liste")
    public ResponseEntity<List<Sujet>> obtenirTousLesSujets() {
        List<Sujet> sujets = sujetService.obtenirTousLesSujets();
        return new ResponseEntity<>(sujets, HttpStatus.OK);
    }

    // Récupérer un sujet par ID
    @GetMapping("/modifier/{id}")
    public ResponseEntity<Sujet> obtenirSujetParId(@PathVariable Long id) {
        Optional<Sujet> sujet = sujetService.obtenirSujetParId(id);
        return sujet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau sujet
    @PostMapping("/creer")
    public ResponseEntity<Sujet> creerSujet(@RequestBody Sujet sujet) {
        Sujet nouveauSujet = sujetService.creerSujet(sujet);
        return new ResponseEntity<>(nouveauSujet, HttpStatus.CREATED);
    }

    // Supprimer un sujet par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerSujet(@PathVariable Long id) {
        sujetService.supprimerSujet(id);
        return ResponseEntity.noContent().build();
    }
}