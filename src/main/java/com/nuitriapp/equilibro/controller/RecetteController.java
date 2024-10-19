package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.ProfilDeSante;
import com.nuitriapp.equilibro.model.Recette;
import com.nuitriapp.equilibro.model.RecetteResponse;
import com.nuitriapp.equilibro.service.EdamamService;
import com.nuitriapp.equilibro.service.ProfilDeSanteService;
import com.nuitriapp.equilibro.service.RecetteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/recettes")
public class RecetteController {

    /*
    private final EdamamService edamamService;
    private final ProfilDeSanteService profilDeSanteService;
    private final RestTemplate restTemplate;

    public RecetteController(EdamamService edamamService, ProfilDeSanteService profilDeSanteService, RestTemplate restTemplate) {
        this.edamamService = edamamService;
        this.profilDeSanteService = profilDeSanteService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/suggestions")
    public RecetteResponse getSuggestions(@RequestParam String query) {
        return edamamService.getRecettes(query);
    }


//    @GetMapping("/profil/{id}")
//    public RecetteResponse getRecettesParProfil(@PathVariable Long id) {
//        ProfilDeSante profil = profilDeSanteService.obtenirProfilParId(id);
//        String query = profilDeSanteService.genererRequeteProfil(profil);
//        return edamamService.getRecettes(query);
//    }


    @GetMapping("/profil/{id}")
    public ResponseEntity<?> getRecettes(@PathVariable int id) {
        try {

            String edamamApiUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=diabetic&app_id=0e990a92&app_key=e8804f773c3f51e803c0d04a578805ee";

            RecetteResponse recettes = restTemplate.getForObject(edamamApiUrl, RecetteResponse.class);
            return ResponseEntity.ok(recettes);
        } catch (Exception e) {
            // Log the error and return a meaningful response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue: " + e.getMessage());
        }
    }

    @GetMapping("/profil/{id}/suggestions-recettes")
    public ResponseEntity<?> getRecettes(@PathVariable Long id) {
        try {
            ProfilDeSante profil = profilDeSanteService.obtenirProfilParId(id);
            String query = profilDeSanteService.genererRequeteProfil(profil);
            String edamamApiUrl = "https://api.edamam.com/api/recipes/v2?q=" + query + "&app_id=0e990a92&app_key=e8804f773c3f51e803c0d04a578805ee&type=public";

            RecetteResponse recettes = restTemplate.getForObject(edamamApiUrl, RecetteResponse.class);
            return ResponseEntity.ok(recettes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue: " + e.getMessage());
        }
    }


    @GetMapping("/profil/{id}/suggestions-recettes")
    public ResponseEntity<?> getSuggestionsRecettes(@PathVariable Long id) {
        try {
            RecetteResponse recettes = edamamService.obtenirSuggestionsRecettes(id);

            // Vérifiez si des recettes ont été trouvées
            if (recettes == null || recettes.getHits().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucune recette trouvée pour les critères spécifiés.");
            }
            return ResponseEntity.ok(recettes);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profil non trouvé.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue: " + e.getMessage());
        }
    }

     */

    /*
    @Autowired
    private RecetteService recetteService;

    @GetMapping("/{label}")
    public ResponseEntity<Recette> getRecette(@PathVariable String label) {
        Recette recette = recetteService.getRecette(label);
        return ResponseEntity.ok(recette);
    }

     */
}
