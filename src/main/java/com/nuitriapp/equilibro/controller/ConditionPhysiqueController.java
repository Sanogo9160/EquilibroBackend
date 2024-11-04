package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.ConditionPhysique;
import com.nuitriapp.equilibro.service.ConditionPhysiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/condition-physique")
public class ConditionPhysiqueController {

    @Autowired
    private ConditionPhysiqueService conditionPhysiqueService;

    @PostMapping("/{profilId}")
    public ResponseEntity<ConditionPhysique> enregistrerCondition(@PathVariable Long profilId, @RequestBody ConditionPhysique condition) {
        ConditionPhysique savedCondition = conditionPhysiqueService.enregistrerCondition(profilId, condition);
        return new ResponseEntity<>(savedCondition, HttpStatus.CREATED);
    }
}
