package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    // Récupérer tous les utilisateurs ayant le rôle DIETETICIEN
    List<Utilisateur> findByRoleNom(String roleNom);

    // Compter le nombre total d'utilisateurs
    @Query("SELECT COUNT(u) FROM Utilisateur u")
    long countTotalUtilisateurs();

    // Compter le nombre de diététiciens
    @Query("SELECT COUNT(d) FROM Dieteticien d")
    long countDieteticiens();

    // Compter le nombre total de consultations
    @Query("SELECT COUNT(c) FROM Consultation c")
    long countConsultations();

}


