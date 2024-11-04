package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.NutritionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class NutritionixService {

    private final RestTemplate restTemplate;

    // Base de données interne de recettes africaines et leurs valeurs nutritionnelles
    private final Map<String, NutritionInfo> africanRecipesDatabase;

    @Value("${nutritionix.api.url}")
    private String apiUrl;

    @Value("${nutritionix.api.app_id}")
    private String appId;

    @Value("${nutritionix.api.app_key}")
    private String appKey;

    public NutritionixService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.africanRecipesDatabase = initializeAfricanRecipes();
    }

    // Initialisation de la base de données locale des recettes africaines
    private Map<String, NutritionInfo> initializeAfricanRecipes() {
        Map<String, NutritionInfo> recipes = new HashMap<>();
        recipes.put("jollof rice", new NutritionInfo(350.0, 15.0, 60.0, 8.0));
        recipes.put("fufu", new NutritionInfo(267.0, 0.2, 65.0, 1.5));
        recipes.put("egusi soup", new NutritionInfo(300.0, 20.0, 12.0, 15.0));
        recipes.put("poulet yassa", new NutritionInfo(400.0, 18.0, 35.0, 25.0));
        // Ajouter d’autres plats africains ici
        return recipes;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public ResponseEntity<String> obtenirSuggestionsRepas(String query) {
        String url = apiUrl + "/search/instant?query=" + query + "&locale=fr_FR";
        HttpEntity<Void> requestEntity = new HttpEntity<>(createHeaders());

        try {
            System.out.println("Envoi de la requête: " + url + " avec headers: " + createHeaders());
            return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            System.err.println("Erreur lors de l'obtention des suggestions : " + e.getStatusCode());
            return ResponseEntity.of(Optional.of("{\"common\":[]}"));
        }
    }

    public NutritionInfo obtenirInfoNutritionnelle(String foodName) {
        // Vérifie si le plat existe dans la base de données interne
        if (africanRecipesDatabase.containsKey(foodName.toLowerCase())) {
            System.out.println("Utilisation des données internes pour le plat africain : " + foodName);
            return africanRecipesDatabase.get(foodName.toLowerCase());
        }

        // Sinon, appelle l'API Nutritionix
        String url = apiUrl + "/natural/nutrients";
        Map<String, String> body = Map.of("query", foodName);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, createHeaders());

        try {
            System.out.println("Requête Nutritionix pour nutrition: " + url + ", Headers: " + createHeaders() + ", Body: " + body);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null || !responseBody.containsKey("foods")) {
                return new NutritionInfo(0.0, 0.0, 0.0, 0.0);
            }

            List<Map<String, Object>> foods = (List<Map<String, Object>>) responseBody.get("foods");
            if (foods.isEmpty()) {
                return new NutritionInfo(0.0, 0.0, 0.0, 0.0);
            }

            Map<String, Object> foodData = foods.get(0);

            Double calories = ((Number) foodData.getOrDefault("nf_calories", 0)).doubleValue();
            Double fat = ((Number) foodData.getOrDefault("nf_total_fat", 0)).doubleValue();
            Double carbohydrates = ((Number) foodData.getOrDefault("nf_total_carbohydrate", 0)).doubleValue();
            Double protein = ((Number) foodData.getOrDefault("nf_protein", 0)).doubleValue();

            return new NutritionInfo(calories, fat, carbohydrates, protein);
        } catch (HttpClientErrorException e) {
            System.err.println("Erreur lors de l'obtention des infos nutritionnelles : " + e.getStatusCode());
            return new NutritionInfo(0.0, 0.0, 0.0, 0.0);
        } catch (Exception e) {
            System.err.println("Erreur inattendue lors de l'obtention des infos nutritionnelles : " + e.getMessage());
            return new NutritionInfo(0.0, 0.0, 0.0, 0.0);
        }
    }

    public List<String> obtenirIngredientsPourRepas(String foodName) {
        // Vérifie si le plat existe dans la base de données interne
        if (africanRecipesDatabase.containsKey(foodName.toLowerCase())) {
            System.out.println("Aucun ingrédient trouvé pour le plat africain dans la base locale : " + foodName);
            return List.of("Ingrédients spécifiques non disponibles dans la base locale");
        }

        // Sinon, appelle l'API Nutritionix
        String url = apiUrl + "/natural/nutrients";
        Map<String, String> body = Map.of("query", foodName);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, createHeaders());

        try {
            System.out.println("Requête Nutritionix pour ingrédients: " + url + ", Headers: " + createHeaders() + ", Body: " + body);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null || !responseBody.containsKey("foods")) {
                return Collections.emptyList();
            }

            List<Map<String, Object>> foods = (List<Map<String, Object>>) responseBody.get("foods");
            if (foods.isEmpty()) {
                return Collections.emptyList();
            }

            Map<String, Object> foodData = foods.get(0);

            if (foodData.containsKey("ingredients")) {
                return (List<String>) foodData.get("ingredients");
            } else if (foodData.containsKey("sub_recipe")) {
                List<Map<String, Object>> subRecipes = (List<Map<String, Object>>) foodData.get("sub_recipe");
                List<String> ingredients = new ArrayList<>();
                for (Map<String, Object> subRecipe : subRecipes) {
                    String ingredientName = (String) subRecipe.get("food_name");
                    if (ingredientName != null) {
                        ingredients.add(ingredientName);
                    }
                }
                return ingredients;
            } else {
                System.out.println("Aucun ingrédient trouvé pour l'aliment : " + foodName);
                return Collections.emptyList();
            }

        } catch (HttpClientErrorException e) {
            System.err.println("Erreur lors de l'obtention des ingrédients : " + e.getStatusCode());
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Erreur inattendue lors de l'obtention des ingrédients : " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
