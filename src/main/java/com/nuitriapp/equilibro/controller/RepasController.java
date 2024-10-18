package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.RecetteResponse;
import com.nuitriapp.equilibro.service.EdamamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/repas")
public class RepasController {

    @Autowired
    private EdamamService edamamService;

    @GetMapping("/suggestions")
    public RecetteResponse getRepasSuggestions(
            @RequestParam String healthLabel,
            @RequestParam String diet,
            @RequestParam int calories) {

        String query = "healthy";

        return edamamService.fetchRecettes(query, healthLabel, diet, calories);
    }


}
