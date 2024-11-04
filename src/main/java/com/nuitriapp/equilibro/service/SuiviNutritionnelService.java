package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.SuiviNutritionnel;
import com.nuitriapp.equilibro.repository.SuiviNutritionnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SuiviNutritionnelService {


    @Autowired
    private SuiviNutritionnelRepository suiviNutritionnelRepository;

    public SuiviNutritionnel enregistrerApport(Long profilId, SuiviNutritionnel suivi) {
        suivi.setProfilId(profilId);
        suivi.setDate(LocalDate.now());
        return suiviNutritionnelRepository.save(suivi);
    }

    public List<SuiviNutritionnel> obtenirApportsParProfil(Long profilId) {
        return suiviNutritionnelRepository.findByProfilId(profilId);
    }


}