package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.ApportNutritionnel;
import com.nuitriapp.equilibro.repository.ApportNutritionnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SuiviNutritionnelService {

    @Autowired
    private ApportNutritionnelRepository apportNutritionnelRepository;

    public void enregistrerApport(ApportNutritionnel apport) {
        apportNutritionnelRepository.save(apport);
    }

    public List<ApportNutritionnel> getApportsParJour(Long utilisateurId, LocalDate date) {
        return apportNutritionnelRepository.findByUtilisateurIdAndDate(utilisateurId, date);
    }
}