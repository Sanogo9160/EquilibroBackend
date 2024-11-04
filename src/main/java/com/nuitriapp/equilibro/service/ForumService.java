package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Forum;
import com.nuitriapp.equilibro.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    public Forum creerForum(Forum forum) {
        return forumRepository.save(forum);
    }

    public List<Forum> listerForums() {
        return forumRepository.findAll();
    }

    public Forum obtenirForum(Long id) {
        return forumRepository.findById(id).orElseThrow(() -> new RuntimeException("Forum non trouvé"));
    }

    public Forum mettreAJourForum(Long id, Forum forum) {
        Forum forumExist = forumRepository.findById(id).orElseThrow(() -> new RuntimeException("Forum non trouvé"));
        forumExist.setNom(forum.getNom());
        forumExist.setDescription(forum.getDescription());
        return forumRepository.save(forumExist);
    }

    public void supprimerForum(Long id) {
        forumRepository.deleteById(id);
    }
}
