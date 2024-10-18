package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Video;
import com.nuitriapp.equilibro.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    // Récupérer toutes les vidéos
    public List<Video> obtenirToutesLesVideos() {
        return videoRepository.findAll();
    }

    // Récupérer une vidéo par ID
    public Optional<Video> obtenirVideoParId(Long id) {
        return videoRepository.findById(id);
    }

    // Créer une nouvelle vidéo
    public Video ajouterVideo(Video video) {
        return videoRepository.save(video);
    }

    // Supprimer une vidéo par ID
    public void supprimerVideo(Long id) {
        videoRepository.deleteById(id);
    }
}