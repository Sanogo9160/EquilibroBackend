package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Forum;
import com.nuitriapp.equilibro.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    // Récupérer tous les forums
    @GetMapping
    public ResponseEntity<List<Forum>> obtenirTousLesForums() {
        List<Forum> forums = forumService.obtenirTousLesForums();
        return new ResponseEntity<>(forums, HttpStatus.OK);
    }

    // Récupérer un forum par ID
    @GetMapping("/{id}")
    public ResponseEntity<Forum> obtenirForumParId(@PathVariable Long id) {
        Optional<Forum> forum = forumService.obtenirForumParId(id);
        return forum.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau forum
    @PostMapping("/creer")
    public ResponseEntity<Forum> creerForum(@RequestBody Forum forum) {
        Forum nouveauForum = forumService.creerForum(forum);
        return new ResponseEntity<>(nouveauForum, HttpStatus.CREATED);
    }

    // Supprimer un forum par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerForum(@PathVariable Long id) {
        forumService.supprimerForum(id);
        return ResponseEntity.noContent().build();
    }

}