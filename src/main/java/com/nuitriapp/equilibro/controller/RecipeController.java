package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.service.EdamamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api")
public class RecipeController {
    private final EdamamService edamamService;

    public RecipeController(EdamamService edamamService) {
        this.edamamService = edamamService;
    }

    /*
    @GetMapping("/recipes")
    public String getRecipes(@RequestParam String query) {
        return edamamService.getRecipes(query);
    }
    */

    @GetMapping("/api/recipes")
    public String getRecipes(@RequestParam(required = false) String query) {
        if (query == null || query.isEmpty()) {
            // Retourner toutes les recettes
            return edamamService.getAllRecipes();
        }
        return edamamService.getRecipes(query);
    }


}