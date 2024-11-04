package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.ConditionPhysique;
import com.nuitriapp.equilibro.repository.ConditionPhysiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConditionPhysiqueService {

    @Autowired
    private ConditionPhysiqueRepository conditionPhysiqueRepository;

    public ConditionPhysique enregistrerCondition(Long profilId, ConditionPhysique condition) {
        condition.setProfilId(profilId);
        condition.setDate(LocalDate.now());
        return conditionPhysiqueRepository.save(condition);
    }

    public List<ConditionPhysique> obtenirConditionsParProfil(Long profilId) {
        return conditionPhysiqueRepository.findByProfilId(profilId);
    }


}
