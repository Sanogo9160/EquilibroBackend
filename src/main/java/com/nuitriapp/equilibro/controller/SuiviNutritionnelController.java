package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.SuiviNutritionnel;
import com.nuitriapp.equilibro.service.SuiviNutritionnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suivi-nutritionnel")
public class SuiviNutritionnelController {

    @Autowired
    private SuiviNutritionnelService suiviNutritionnelService;

    @PostMapping("/{profilId}")
    public ResponseEntity<SuiviNutritionnel> enregistrerApport(@PathVariable Long profilId, @RequestBody SuiviNutritionnel suivi) {
        SuiviNutritionnel savedSuivi = suiviNutritionnelService.enregistrerApport(profilId, suivi);
        return new ResponseEntity<>(savedSuivi, HttpStatus.CREATED);
    }
}
