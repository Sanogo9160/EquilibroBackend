package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {

    // Méthode pour récupérer les disponibilités par ID de diététicien
    List<Disponibilite> findByDieteticienId(Long id);

}