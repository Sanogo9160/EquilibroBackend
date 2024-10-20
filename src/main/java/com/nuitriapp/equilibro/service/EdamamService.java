package com.nuitriapp.equilibro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuitriapp.equilibro.model.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    public List<Recipe> getRecipesFromApi() {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.edamam.com/search")
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey)
                .queryParam("q", "pasta")  // Exemple mot-clé obligatoire
                .queryParam("lang", "fr")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                System.err.println("Erreur API : statut " + response.getStatusCode());
                return new ArrayList<>();
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode hits = root.path("hits");

            if (hits.isEmpty()) {
                System.out.println("Aucune recette trouvée.");
                return new ArrayList<>();
            }

            // Mapping des recettes
            return StreamSupport.stream(hits.spliterator(), false)
                    .map(hit -> hit.path("recipe"))
                    .map(this::mapJsonToRecipe)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Recipe mapJsonToRecipe(JsonNode recipeNode) {
        Recipe recipe = new Recipe();
        recipe.setLabel(recipeNode.path("label").asText("Recette sans nom"));
        recipe.setImage(recipeNode.path("image").asText(""));
        recipe.setUrl(recipeNode.path("url").asText(""));
        recipe.setCookingTime(recipeNode.path("totalTime").asInt(0));

        recipe.setIngredients(StreamSupport.stream(recipeNode.path("ingredients").spliterator(), false)
                .map(ingredientNode -> ingredientNode.path("text").asText("Ingrédient inconnu"))
                .collect(Collectors.toList()));

        recipe.setNutritionalInfo(recipeNode.path("totalNutrients"));

        JsonNode healthLabels = recipeNode.path("healthLabels");
        if (healthLabels.isArray() && StreamSupport.stream(healthLabels.spliterator(), false)
                .anyMatch(label -> label.asText().equalsIgnoreCase("Vegetarian"))) {
            recipe.setCategory("vegetarian");
        } else {
            recipe.setCategory("carnivore");
        }

        return recipe;
    }

}
