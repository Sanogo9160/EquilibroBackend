package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.PreferenceAlimentaire;
import com.nuitriapp.equilibro.service.PreferenceAlimentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences-alimentaires")
public class PreferenceAlimentaireController {

    @Autowired
    private PreferenceAlimentaireService preferenceAlimentaireService;

    @GetMapping("/")
    public List<PreferenceAlimentaire> obtenirToutesLesPreferences() {
        return preferenceAlimentaireService.obtenirToutesLesPreferences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenceAlimentaire> obtenirPreferenceParId(@PathVariable Long id) {
        PreferenceAlimentaire preference = preferenceAlimentaireService.obtenirPreferenceParId(id);
        return preference != null ? new ResponseEntity<>(preference, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<PreferenceAlimentaire> ajouterPreference(@RequestBody PreferenceAlimentaire preference) {
        PreferenceAlimentaire nouvellePreference = preferenceAlimentaireService.ajouterPreference(preference);
        return new ResponseEntity<>(nouvellePreference, HttpStatus.CREATED);
    }

    @PutMapping("/mettreAJour/{id}")
    public ResponseEntity<PreferenceAlimentaire> mettreAJourPreference(@PathVariable Long id, @RequestBody PreferenceAlimentaire preference) {
        PreferenceAlimentaire preferenceMiseAJour = preferenceAlimentaireService.mettreAJourPreference(id, preference);
        return preferenceMiseAJour != null ? new ResponseEntity<>(preferenceMiseAJour, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<HttpStatus> supprimerPreference(@PathVariable Long id) {
        preferenceAlimentaireService.supprimerPreference(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
