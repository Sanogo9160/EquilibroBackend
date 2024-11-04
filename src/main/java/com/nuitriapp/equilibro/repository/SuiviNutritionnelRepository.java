package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.SuiviNutritionnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SuiviNutritionnelRepository extends JpaRepository<SuiviNutritionnel, Long> {
    List<SuiviNutritionnel> findByProfilId(Long profilId);
}
