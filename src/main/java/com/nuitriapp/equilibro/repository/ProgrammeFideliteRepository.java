package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.ProgrammeFidelite;
import com.nuitriapp.equilibro.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammeFideliteRepository extends JpaRepository<ProgrammeFidelite, Long> {

    ProgrammeFidelite findByUtilisateur(Utilisateur utilisateur);

}
