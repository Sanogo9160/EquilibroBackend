package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}