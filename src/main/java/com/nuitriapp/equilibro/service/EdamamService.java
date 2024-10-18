package com.nuitriapp.equilibro.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuitriapp.equilibro.model.Recette;
import com.nuitriapp.equilibro.model.RecetteResponse;
import com.nuitriapp.equilibro.repository.ProfilDeSanteRepository;
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

    @Value("${edamam.api.url}")
    private String edamamApiUrl;

    @Value("${edamam.api.app_id}")
    private String appId;

    @Value("${edamam.api.app_key}")
    private String appKey;

    private final RestTemplate restTemplate; // RestTemplate pour effectuer les requêtes HTTP
    private final ProfilDeSanteRepository profilDeSanteRepository; // Référentiel pour les profils de santé
    private final ObjectMapper objectMapper; // ObjectMapper de Jackson pour la gestion des JSON

    public EdamamService(ProfilDeSanteRepository profilDeSanteRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.profilDeSanteRepository = profilDeSanteRepository; // Initialisation du référentiel
        this.restTemplate = restTemplate; // Initialisation de RestTemplate
        this.objectMapper = objectMapper; // Initialisation de ObjectMapper
    }

    /**
     * Méthode pour récupérer une recette à partir de l'API Edamam.
     * @param label Le nom de la recette à rechercher.
     * @return Un objet Recette contenant les détails de la recette.
     */
    public Recette fetchRecetteFromAPI(String label) {
        String url = UriComponentsBuilder.fromHttpUrl(edamamApiUrl)
                .queryParam("q", label)
                .queryParam("type", "public")
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class); // Effectuer la requête GET
            JsonNode rootNode = objectMapper.readTree(response); // Parser la réponse JSON

            if (rootNode.has("hits") && rootNode.get("hits").isArray() && rootNode.get("hits").size() > 0) {
                JsonNode recetteJson = rootNode.get("hits").get(0).get("recipe"); // Obtenir la première recette

                Recette recette = new Recette(); // Créer un nouvel objet Recette
                recette.setLabel(recetteJson.get("label").asText()); // Définir le label de la recette
                recette.setUrl(recetteJson.get("url").asText()); // Définir l'URL de la recette
                recette.setSource(recetteJson.get("source").asText()); // Définir la source de la recette
                recette.setCalories(recetteJson.get("calories").asDouble()); // Définir les calories
                recette.setCarbs(recetteJson.get("totalNutrients").get("CHOCDF").get("quantity").asDouble()); // Définir les glucides
                recette.setFat(recetteJson.get("totalNutrients").get("FAT").get("quantity").asDouble()); // Définir les graisses
                recette.setProtein(recetteJson.get("totalNutrients").get("PROCNT").get("quantity").asDouble()); // Définir les protéines

                // Convertir les lignes d'ingrédients en liste
                recette.setIngredients(convertJsonNodeToList(recetteJson.get("ingredientLines")));

                return recette; // Retourner l'objet Recette
            } else {
                throw new RuntimeException("Aucune recette trouvée pour le label : " + label);
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la recette depuis l'API : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération de la recette", e);
        }
    }

    /**
     * Méthode pour récupérer plusieurs recettes à partir de l'API Edamam en fonction d'une requête.
     * @param query La requête de recherche pour les recettes.
     * @param healthLabel Étiquette de santé (optionnel).
     * @param diet Étiquette de régime (optionnel).
     * @param calories Limitation des calories (optionnel).
     * @return Un objet RecetteResponse contenant la liste des recettes.
     */
    public RecetteResponse fetchRecettes(String query, String healthLabel, String diet, int calories) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(edamamApiUrl)
                .queryParam("q", query)
                .queryParam("type", "public")
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey);

        if (healthLabel != null && !healthLabel.isEmpty()) {
            builder.queryParam("health", healthLabel);
        }

        if (diet != null && !diet.isEmpty()) {
            builder.queryParam("diet", diet);
        }

        if (calories > 0) {
            builder.queryParam("calories", calories);
        }

        String url = builder.toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class); // Effectuer la requête GET
            JsonNode rootNode = objectMapper.readTree(response); // Parser la réponse JSON

            List<Recette> recettes = new ArrayList<>(); // Liste pour stocker les recettes

            if (rootNode.has("hits") && rootNode.get("hits").isArray()) {
                // Parcourir le tableau "hits" et extraire les recettes
                for (JsonNode hit : rootNode.get("hits")) {
                    JsonNode recetteJson = hit.get("recipe"); // Obtenir les détails de la recette
                    Recette recette = new Recette(); // Créer un nouvel objet Recette
                    recette.setLabel(recetteJson.get("label").asText()); // Définir le label
                    recette.setUrl(recetteJson.get("url").asText()); // Définir l'URL
                    recette.setSource(recetteJson.get("source").asText()); // Définir la source
                    recette.setCalories(recetteJson.get("calories").asDouble()); // Définir les calories
                    recette.setCarbs(recetteJson.get("totalNutrients").get("CHOCDF").get("quantity").asDouble()); // Définir les glucides
                    recette.setFat(recetteJson.get("totalNutrients").get("FAT").get("quantity").asDouble()); // Définir les graisses
                    recette.setProtein(recetteJson.get("totalNutrients").get("PROCNT").get("quantity").asDouble()); // Définir les protéines

                    // Convertir les lignes d'ingrédients en liste
                    recette.setIngredients(convertJsonNodeToList(recetteJson.get("ingredientLines")));

                    // Ajouter la recette à la liste
                    recettes.add(recette);
                }
            }
            // Créer et retourner un objet RecetteResponse contenant la liste des recettes
            RecetteResponse recetteResponse = new RecetteResponse(); // Utilisation de RecetteReponse au lieu de RecetteResponse
            recetteResponse.setRecettes(recettes); // Définir la liste des recettes
            recetteResponse.setCount(recettes.size()); // Définir le nombre de recettes
            return recetteResponse; // Retourner l'objet RecetteReponse

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des recettes depuis l'API : " + e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des recettes", e);
        }
    }

    // Méthode utilitaire pour convertir JsonNode en List<String>
    private List<String> convertJsonNodeToList(JsonNode jsonNode) {
        if (jsonNode != null && jsonNode.isArray()) {
            return StreamSupport.stream(jsonNode.spliterator(), false)
                    .map(JsonNode::asText)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(); // Retourner une liste vide si ce n'est pas un tableau
    }


}

