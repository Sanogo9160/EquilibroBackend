package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.ProgrammeFidelite;
import com.nuitriapp.equilibro.service.ProgrammeFideliteService;
import com.nuitriapp.equilibro.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fidelite")
public class ProgrammeFideliteController {

    @Autowired
    private ProgrammeFideliteService programmeFideliteService;

    // Lister tous les programmes de fidélité
    @GetMapping("/liste")
    public List<ProgrammeFidelite> listerProgrammesFidelite() {
        return programmeFideliteService.listerProgrammesFidelite();
    }

    // Obtenir un programme de fidélité par ID
    @GetMapping("/obtenir/{id}")
    public ResponseEntity<ProgrammeFidelite> obtenirProgrammeFideliteParId(@PathVariable Long id) {
        return programmeFideliteService.obtenirProgrammeFideliteParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouveau programme de fidélité
    @PostMapping("/creer")
    public ProgrammeFidelite creerProgrammeFidelite(@RequestBody ProgrammeFidelite programmeFidelite) {
        return programmeFideliteService.creerProgrammeFidelite(programmeFidelite);
    }

    // Ajouter des points à un programme de fidélité existant
    @PutMapping("/{id}/ajouter-points")
    public ResponseEntity<ProgrammeFidelite> ajouterPoints(@PathVariable Long id, @RequestParam Integer pointsAjoutes) {
        ProgrammeFidelite programme = programmeFideliteService.ajouterPoints(id, pointsAjoutes);
        return ResponseEntity.ok(programme);
    }

    // Utiliser des points (rédemption)
    @PutMapping("/{id}/utiliser-points")
    public ResponseEntity<ProgrammeFidelite> utiliserPoints(@PathVariable Long id, @RequestParam Integer pointsUtilises) {
        ProgrammeFidelite programme = programmeFideliteService.utiliserPoints(id, pointsUtilises);
        return ResponseEntity.ok(programme);
    }

    // Supprimer un programme de fidélité
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProgrammeFidelite(@PathVariable Long id) {
        programmeFideliteService.supprimerProgrammeFidelite(id);
        return ResponseEntity.noContent().build();
    }
}