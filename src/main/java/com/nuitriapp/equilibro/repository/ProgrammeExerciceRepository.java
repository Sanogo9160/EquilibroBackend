package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.ProgrammeExercice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgrammeExerciceRepository extends JpaRepository<ProgrammeExercice, Long> {
    List<ProgrammeExercice> findByUtilisateurId(Long utilisateurId);
}
