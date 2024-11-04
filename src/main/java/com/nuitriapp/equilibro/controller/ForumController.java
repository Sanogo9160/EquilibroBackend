package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Commentaire;
import com.nuitriapp.equilibro.model.Forum;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.service.CommentaireService;
import com.nuitriapp.equilibro.service.ForumService;
import com.nuitriapp.equilibro.service.UtilisateurDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private UtilisateurDetailsService utilisateurDetailsService;

    @Autowired
    private CommentaireService commentaireService;

    @PostMapping("/creer")
    public ResponseEntity<Forum> creerForum(@RequestBody Forum forum) {
        try {
            Utilisateur utilisateur = utilisateurDetailsService.getCurrentUser();
            forum.setAuteur(utilisateur);
            forum.setDateCreation(OffsetDateTime.now());  // Assigner la date actuelle avec fuseau horaire
            Forum forumSauvegarde = forumService.creerForum(forum);
            return ResponseEntity.ok(forumSauvegarde);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/obtenir")
    public ResponseEntity<List<Forum>> listerForums() {
        try {
            List<Forum> forums = forumService.listerForums();
            return ResponseEntity.ok(forums);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forum> obtenirForum(@PathVariable Long id) {
        try {
            Forum forum = forumService.obtenirForum(id);
            return ResponseEntity.ok(forum);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Forum> mettreAJourForum(@PathVariable Long id, @RequestBody Forum forum) {
        try {
            Forum forumMisAJour = forumService.mettreAJourForum(id, forum);
            return ResponseEntity.ok(forumMisAJour);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerForum(@PathVariable Long id) {
        try {
            forumService.supprimerForum(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{forumId}/commentaires")
    public ResponseEntity<Commentaire> ajouterCommentaire(@PathVariable Long forumId, @RequestBody Commentaire commentaire) {
        try {
            Forum forum = forumService.obtenirForum(forumId);
            Utilisateur utilisateur = utilisateurDetailsService.getCurrentUser();
            commentaire.setForum(forum);
            commentaire.setAuteur(utilisateur);
            Commentaire commentaireSauvegarde = commentaireService.creerCommentaire(commentaire);
            return ResponseEntity.ok(commentaireSauvegarde);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{forumId}/commentaires")
    public ResponseEntity<List<Commentaire>> listerCommentaires(@PathVariable Long forumId) {
        try {
            Forum forum = forumService.obtenirForum(forumId);
            return ResponseEntity.ok(forum.getCommentaires());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
