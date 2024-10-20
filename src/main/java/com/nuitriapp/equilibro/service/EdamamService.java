package com.nuitriapp.equilibro.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuitriapp.equilibro.model.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EdamamService {

    @Value("${edamam.applicationId}")
    private String appId;

    @Value("${edamam.applicationKey}")
    private String appKey;

    private final RestTemplate restTemplate;

    public EdamamService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<Recipe> getRecipesFromApi(String query) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.edamam.com/search")
                .queryParam("q", query)
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        // Afficher la réponse JSON brute pour voir son contenu
        System.out.println("Réponse brute de l'API Edamam : " + response);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode hits = root.path("hits"); // Extraire le tableau 'hits' dans la réponse Edamam

            // Vérifier si 'hits' contient bien des données
            if (hits.isEmpty()) {
                System.out.println("Aucune recette trouvée dans les hits.");
                return new ArrayList<>();
            }

            // Extraire les informations des recettes
            return StreamSupport.stream(hits.spliterator(), false)
                    .map(hit -> hit.path("recipe"))
                    .map(recipeNode -> {
                        Recipe recipe = new Recipe();
                        recipe.setLabel(recipeNode.path("label").asText());
                        recipe.setImage(recipeNode.path("image").asText());
                        recipe.setUrl(recipeNode.path("url").asText());
                        recipe.setCookingTime(recipeNode.path("totalTime").asInt(0));

                        return recipe;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}

