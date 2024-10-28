package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Commentaire;
import com.nuitriapp.equilibro.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;

    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> obtenirCommentaire(@PathVariable Long id) {
        Commentaire commentaire = commentaireService.obtenirCommentaire(id);
        return ResponseEntity.ok(commentaire);
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Commentaire> mettreAJourCommentaire(@PathVariable Long id, @RequestBody Commentaire commentaire) {
        Commentaire commentaireMisAJour = commentaireService.mettreAJourCommentaire(id, commentaire);
        return ResponseEntity.ok(commentaireMisAJour);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerCommentaire(@PathVariable Long id) {
        commentaireService.supprimerCommentaire(id);
        return ResponseEntity.noContent().build();
    }
}
