package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Forum;
import com.nuitriapp.equilibro.model.Message;
import com.nuitriapp.equilibro.repository.ForumRepository;
import com.nuitriapp.equilibro.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepository;

    // Récupérer tous les forums
    public List<Forum> obtenirTousLesForums() {
        return forumRepository.findAll();
    }

    // Récupérer un forum par ID
    public Optional<Forum> obtenirForumParId(Long id) {
        return forumRepository.findById(id);
    }

    // Créer un nouveau forum
    public Forum creerForum(Forum forum) {
        return forumRepository.save(forum);
    }

    // Supprimer un forum par ID
    public void supprimerForum(Long id) {
        forumRepository.deleteById(id);
    }

}