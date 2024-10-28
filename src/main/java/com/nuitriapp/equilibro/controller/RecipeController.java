package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.config.JwtUtil;
import com.nuitriapp.equilibro.model.ProfilDeSante;
import com.nuitriapp.equilibro.model.Recipe;
import com.nuitriapp.equilibro.service.EdamamService;
import com.nuitriapp.equilibro.service.ProfilDeSanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:59986"})
@RequestMapping("/api")
public class RecipeController {

    private final EdamamService edamamService;
    private final JwtUtil jwtUtil;
    private final ProfilDeSanteService profilDeSanteService;

    public RecipeController(EdamamService edamamService, JwtUtil jwtUtil, ProfilDeSanteService profilDeSanteService) {
        this.edamamService = edamamService;
        this.jwtUtil = jwtUtil;
        this.profilDeSanteService = profilDeSanteService;
    }

    // Récupérer des recettes générales sans filtrage par profil de santé
    @GetMapping("/recipes")
    public Map<String, List<Recipe>> getRecipes() {
        List<Recipe> recipes = edamamService.getRecipesFromApi(); // Cette méthode doit être mise à jour
        return recipes.stream().collect(Collectors.groupingBy(Recipe::getCategory));
    }

    // Générer un plan de repas personnalisé pour l'utilisateur
    @GetMapping("/plan-repas")
    public ResponseEntity<List<Recipe>> getMealPlanForUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        ProfilDeSante profilDeSante = profilDeSanteService.obtenirProfilParEmail(email);
        if (profilDeSante == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Recipe> mealPlan = edamamService.getRecipesFromApi(profilDeSante); // Appel avec le profil de santé
        if (mealPlan.isEmpty()) {
            mealPlan = edamamService.getRecipesFromApi();  // Plan de secours sans restrictions
        }

        return new ResponseEntity<>(mealPlan, HttpStatus.OK);
    }
}
