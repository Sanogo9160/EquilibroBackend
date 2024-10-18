package com.nuitriapp.equilibro.controller;


import com.nuitriapp.equilibro.model.Video;
import com.nuitriapp.equilibro.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    @Autowired
    private VideoService videoService;

    // Récupérer toutes les vidéos
    @GetMapping("/liste")
    public ResponseEntity<List<Video>> obtenirToutesLesVideos() {
        List<Video> videos = videoService.obtenirToutesLesVideos();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    // Récupérer une vidéo par ID
    @GetMapping("/obtenir/{id}")
    public ResponseEntity<Video> obtenirVideoParId(@PathVariable Long id) {
        Optional<Video> video = videoService.obtenirVideoParId(id);
        return video.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer une nouvelle vidéo
    @PostMapping("/ajouter")
    public ResponseEntity<Video> ajouterVideo(@RequestBody Video video) {
        Video nouvelleVideo = videoService.ajouterVideo(video);
        return new ResponseEntity<>(nouvelleVideo, HttpStatus.CREATED);
    }

    // Supprimer une vidéo par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerVideo(@PathVariable Long id) {
        videoService.supprimerVideo(id);
        return ResponseEntity.noContent().build();
    }

}
