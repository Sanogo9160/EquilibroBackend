package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Allergie;
import com.nuitriapp.equilibro.service.AllergieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allergies")
public class AllergieController {

    @Autowired
    private AllergieService allergieService;

    @GetMapping("/liste")
    public List<Allergie> obtenirToutesLesAllergies() {
        return allergieService.obtenirToutesLesAllergies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Allergie> obtenirAllergieParId(@PathVariable Long id) {
        Allergie allergie = allergieService.obtenirAllergieParId(id);
        return allergie != null ? new ResponseEntity<>(allergie, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Allergie> ajouterAllergie(@RequestBody Allergie allergie) {
        Allergie nouvelleAllergie = allergieService.ajouterAllergie(allergie);
        return new ResponseEntity<>(nouvelleAllergie, HttpStatus.CREATED);
    }

    @PutMapping("/mettreAJour/{id}")
    public ResponseEntity<Allergie> mettreAJourAllergie(@PathVariable Long id, @RequestBody Allergie allergie) {
        Allergie allergieMiseAJour = allergieService.mettreAJourAllergie(id, allergie);
        return allergieMiseAJour != null ? new ResponseEntity<>(allergieMiseAJour, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<HttpStatus> supprimerAllergie(@PathVariable Long id) {
        allergieService.supprimerAllergie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
