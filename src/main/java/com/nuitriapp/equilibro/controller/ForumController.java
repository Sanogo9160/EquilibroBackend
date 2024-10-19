package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Commentaire;
import com.nuitriapp.equilibro.model.Forum;
import com.nuitriapp.equilibro.model.Utilisateur;
import com.nuitriapp.equilibro.repository.ForumRepository;
import com.nuitriapp.equilibro.service.CommentaireService;
import com.nuitriapp.equilibro.service.ForumService;
import com.nuitriapp.equilibro.service.UtilisateurDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        Utilisateur utilisateur = utilisateurDetailsService.getCurrentUser();
        forum.setAuteur(utilisateur);
        Forum forumSauvegarde = forumService.creerForum(forum);
        return ResponseEntity.ok(forumSauvegarde);
    }

    @GetMapping("/obtenir")
    public List<Forum> listerForums() {
        return forumService.listerForums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forum> obtenirForum(@PathVariable Long id) {
        Forum forum = forumService.obtenirForum(id);
        return ResponseEntity.ok(forum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Forum> mettreAJourForum(@PathVariable Long id, @RequestBody Forum forum) {
        Forum forumMisAJour = forumService.mettreAJourForum(id, forum);
        return ResponseEntity.ok(forumMisAJour);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerForum(@PathVariable Long id) {
        forumService.supprimerForum(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{forumId}/commentaires")
    public ResponseEntity<Commentaire> ajouterCommentaire(@PathVariable Long forumId, @RequestBody Commentaire commentaire) {
        Forum forum = forumService.obtenirForum(forumId);
        Utilisateur utilisateur = utilisateurDetailsService.getCurrentUser();
        commentaire.setForum(forum);
        commentaire.setAuteur(utilisateur);
        Commentaire commentaireSauvegarde = commentaireService.creerCommentaire(commentaire);
        return ResponseEntity.ok(commentaireSauvegarde);
    }

    @GetMapping("/{forumId}/commentaires")
    public List<Commentaire> listerCommentaires(@PathVariable Long forumId) {
        Forum forum = forumService.obtenirForum(forumId);
        return forum.getCommentaires();
    }

}