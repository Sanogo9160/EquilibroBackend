package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.ApportNutritionnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ApportNutritionnelRepository extends JpaRepository<ApportNutritionnel, Long> {

    List<ApportNutritionnel> findByUtilisateurIdAndDate(Long utilisateurId, LocalDate date);

}
