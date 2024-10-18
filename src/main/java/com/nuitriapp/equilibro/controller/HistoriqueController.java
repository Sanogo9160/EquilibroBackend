package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Historique;
import com.nuitriapp.equilibro.service.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historiques")
public class HistoriqueController {

    @Autowired
    private HistoriqueService historiqueService;

    // Obtenir tous les historiques
    @GetMapping("/liste")
    public ResponseEntity<List<Historique>> obtenirTousLesHistoriques() {
        List<Historique> historiques = historiqueService.obtenirTousLesHistoriques();
        return ResponseEntity.ok(historiques);
    }

    // Obtenir un historique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Historique> obtenirHistoriqueParId(@PathVariable Long id) {
        Optional<Historique> historique = historiqueService.obtenirHistoriqueParId(id);
        return historique.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouvel historique
    @PostMapping("/creer")
    public ResponseEntity<Historique> creerHistorique(@RequestBody Historique historique) {
        Historique nouvelHistorique = historiqueService.creerHistorique(historique);
        return ResponseEntity.ok(nouvelHistorique);
    }

    // Supprimer un historique
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerHistorique(@PathVariable Long id) {
        historiqueService.supprimerHistorique(id);
        return ResponseEntity.noContent().build();
    }

}
