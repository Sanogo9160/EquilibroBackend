package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Maladie;
import com.nuitriapp.equilibro.service.MaladieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maladies")
public class MaladieController {

    @Autowired
    private MaladieService maladieService;

    @GetMapping("/Liste")
    public List<Maladie> obtenirToutesLesMaladies() {
        return maladieService.obtenirToutesLesMaladies();
    }

    @GetMapping("obtenir/{id}")
    public ResponseEntity<Maladie> obtenirMaladieParId(@PathVariable Long id) {
        Maladie maladie = maladieService.obtenirMaladieParId(id);
        return maladie != null ? new ResponseEntity<>(maladie, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Maladie> ajouterMaladie(@RequestBody Maladie maladie) {
        Maladie nouvelleMaladie = maladieService.ajouterMaladie(maladie);
        return new ResponseEntity<>(nouvelleMaladie, HttpStatus.CREATED);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Maladie> mettreAJourMaladie(@PathVariable Long id, @RequestBody Maladie maladie) {
        Maladie maladieMiseAJour = maladieService.mettreAJourMaladie(id, maladie);
        return maladieMiseAJour != null ? new ResponseEntity<>(maladieMiseAJour, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<HttpStatus> supprimerMaladie(@PathVariable Long id) {
        maladieService.supprimerMaladie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
