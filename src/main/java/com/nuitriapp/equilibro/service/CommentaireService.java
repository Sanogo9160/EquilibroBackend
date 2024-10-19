package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Commentaire;
import com.nuitriapp.equilibro.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    public Commentaire obtenirCommentaire(Long id) {
        return commentaireRepository.findById(id).orElseThrow();
    }

    // La méthode pour créer un commentaire
    public Commentaire creerCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    public Commentaire mettreAJourCommentaire(Long id, Commentaire commentaire) {
        Commentaire commentaireExist = commentaireRepository.findById(id).orElseThrow();
        commentaireExist.setContenu(commentaire.getContenu());
        return commentaireRepository.save(commentaireExist);
    }

    public void supprimerCommentaire(Long id) {
        commentaireRepository.deleteById(id);
    }

}