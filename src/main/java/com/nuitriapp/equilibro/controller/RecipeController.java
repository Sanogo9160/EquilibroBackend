package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Recipe;
import com.nuitriapp.equilibro.service.EdamamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/api")
public class RecipeController {

    private final EdamamService edamamService;

    public RecipeController(EdamamService edamamService) {
        this.edamamService = edamamService;
    }

    @GetMapping("/recipes")
    public List<Recipe> getRecipes(@RequestParam String query) {
        return edamamService.getRecipesFromApi(query);
    }
}