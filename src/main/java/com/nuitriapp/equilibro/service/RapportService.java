package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.ConditionPhysique;
import com.nuitriapp.equilibro.model.SuiviNutritionnel;
import com.nuitriapp.equilibro.repository.ConditionPhysiqueRepository;
import com.nuitriapp.equilibro.repository.SuiviNutritionnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapportService {

    @Autowired
    private SuiviNutritionnelRepository suiviNutritionnelRepository;
    @Autowired
    private ConditionPhysiqueRepository conditionPhysiqueRepository;

    public String genererRapport(Long profilId) {
        List<SuiviNutritionnel> apports = suiviNutritionnelRepository.findByProfilId(profilId);
        List<ConditionPhysique> conditions = conditionPhysiqueRepository.findByProfilId(profilId);

        // Calcule des moyennes pour chaque paramètre
        int totalCalories = apports.stream().mapToInt(SuiviNutritionnel::getCalories).sum();
        double poidsMoyen = conditions.stream().mapToDouble(ConditionPhysique::getPoids).average().orElse(0.0);

        return String.format("Rapport pour le profil %d:\nCalories Moyennes: %d\nPoids Moyen: %.2f kg", profilId, totalCalories / apports.size(), poidsMoyen);
    }

}
