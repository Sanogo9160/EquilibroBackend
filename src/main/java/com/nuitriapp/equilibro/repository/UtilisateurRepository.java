package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    // Récupérer tous les utilisateurs ayant le rôle DIETETICIEN
    List<Utilisateur> findByRoleNom(String roleNom);


}


