package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Recette;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecetteRepository extends JpaRepository<Recette, Integer> {

    Optional<Recette> findByLabel(String label);

}
