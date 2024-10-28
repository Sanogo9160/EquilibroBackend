package com.nuitriapp.equilibro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuitriapp.equilibro.model.ProfilDeSante;
import com.nuitriapp.equilibro.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
    private final LibreTranslateService libreTranslateService;

    private static final List<String> ALLOWED_LABELS = List.of(
            "low-carb", "low-fat", "sugar-conscious",
            "gluten-free", "dairy-free", "keto", "paleo", "vegan"
    );

    @Autowired
    public EdamamService(RestTemplate restTemplate, LibreTranslateService libreTranslateService) {
        this.restTemplate = restTemplate;
        this.libreTranslateService = libreTranslateService;
    }

    private boolean isLabelAllowed(String label) {
        return ALLOWED_LABELS.contains(label.toLowerCase());
    }

    public List<Recipe> getRecipesFromApi() {
        // Appel de la méthode principale avec un profil de santé par défaut
        return getRecipesFromApi(new ProfilDeSante());
    }

    public List<Recipe> getRecipesFromApi(ProfilDeSante profilDeSante) {
        String query = "meal";  // Mot-clé général pour la requête

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://api.edamam.com/search")
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey)
                .queryParam("q", query);

        // Ajouter les allergies et préférences alimentaires
        if (profilDeSante != null) {
            profilDeSante.getAllergies().stream()
                    .map(allergie -> allergie.getNom())
                    .filter(this::isLabelAllowed)
                    .forEach(label -> uriBuilder.queryParam("health", label));

            profilDeSante.getPreferencesAlimentaires().stream()
                    .map(pref -> pref.getNom())
                    .filter(this::isLabelAllowed)
                    .forEach(label -> uriBuilder.queryParam("diet", label));
        }

        return executeApiRequest(uriBuilder.toUriString());
    }

    private List<Recipe> executeApiRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            System.out.println("Réponse de l'API : " + response.getBody());

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

            List<String> itemsToTranslate = new ArrayList<>();
            List<JsonNode> recipeNodes = new ArrayList<>();

            // Collecte des éléments à traduire
            hits.forEach(hit -> {
                JsonNode recipeNode = hit.path("recipe");
                recipeNodes.add(recipeNode);
                itemsToTranslate.add(recipeNode.path("label").asText("Recette sans nom"));
                recipeNode.path("ingredients").forEach(ingredientNode ->
                        itemsToTranslate.add(ingredientNode.path("text").asText("Ingrédient inconnu")));
            });

            // Traduction unique de tous les éléments
            List<String> translatedItems = libreTranslateService.translateTexts(itemsToTranslate, "fr");

            int translateIndex = 0;
            List<Recipe> recipes = new ArrayList<>();
            for (JsonNode recipeNode : recipeNodes) {
                Recipe recipe = new Recipe();

                recipe.setLabel(translatedItems.get(translateIndex++));
                recipe.setImage(recipeNode.path("image").asText(""));
                recipe.setUrl(recipeNode.path("url").asText(""));
                recipe.setCookingTime(recipeNode.path("totalTime").asInt(0));

                List<String> ingredients = new ArrayList<>();
                for (JsonNode ingredientNode : recipeNode.path("ingredients")) {
                    ingredients.add(translatedItems.get(translateIndex++));
                }
                recipe.setIngredients(ingredients);

                recipe.setNutritionalInfo(recipeNode.path("totalNutrients"));

                JsonNode healthLabels = recipeNode.path("healthLabels");
                recipe.setCategory(healthLabels.isArray() && StreamSupport.stream(healthLabels.spliterator(), false)
                        .anyMatch(label -> label.asText().equalsIgnoreCase("Vegetarian")) ? "végétarien" : "carnivore");

                recipes.add(recipe);
            }

            return recipes;

        } catch (HttpClientErrorException.Forbidden e) {
            System.err.println("Erreur 403 : Certains filtres ne sont pas autorisés pour votre plan.");
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
