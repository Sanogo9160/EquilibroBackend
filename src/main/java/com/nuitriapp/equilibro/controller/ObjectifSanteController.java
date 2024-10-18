package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.ObjectifSante;
import com.nuitriapp.equilibro.service.ObjectifSanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objectifs-sante")
public class ObjectifSanteController {
    @Autowired
    private ObjectifSanteService objectifSanteService;

    @GetMapping("/liste")
    public List<ObjectifSante> obtenirTousLesObjectifs() {
        return objectifSanteService.obtenirTousLesObjectifs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectifSante> obtenirObjectifParId(@PathVariable Long id) {
        ObjectifSante objectifSante = objectifSanteService.obtenirObjectifParId(id);
        return objectifSante != null ? new ResponseEntity<>(objectifSante, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ajouter")
    public ResponseEntity<ObjectifSante> ajouterObjectif(@RequestBody ObjectifSante objectifSante) {
        ObjectifSante nouvelObjectif = objectifSanteService.ajouterObjectif(objectifSante);
        return new ResponseEntity<>(nouvelObjectif, HttpStatus.CREATED);
    }

    @PutMapping("/mettreAJour/{id}")
    public ResponseEntity<ObjectifSante> mettreAJourObjectif(@PathVariable Long id, @RequestBody ObjectifSante objectifSante) {
        ObjectifSante objectifMisAJour = objectifSanteService.mettreAJourObjectif(id, objectifSante);
        return objectifMisAJour != null ? new ResponseEntity<>(objectifMisAJour, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<HttpStatus> supprimerObjectif(@PathVariable Long id) {
        objectifSanteService.supprimerObjectif(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
