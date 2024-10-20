package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Recipe;
import com.nuitriapp.equilibro.service.EdamamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api")
public class RecipeController {

    private final EdamamService edamamService;

    public RecipeController(EdamamService edamamService) {
        this.edamamService = edamamService;
    }

    @GetMapping("/recipes")
    public Map<String, List<Recipe>> getRecipes() {
        List<Recipe> recipes = edamamService.getRecipesFromApi();

        // Groupement par catégories
        return recipes.stream()
                .collect(Collectors.groupingBy(Recipe::getCategory));
    }

}