package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.ProfilDeSante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilDeSanteRepository extends JpaRepository<ProfilDeSante, Long> {

    ProfilDeSante findByUtilisateurId(Long utilisateurId);

}
