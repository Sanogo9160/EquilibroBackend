package com.nuitriapp.equilibro.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuitriapp.equilibro.model.*;
import com.nuitriapp.equilibro.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfilDeSanteService {

    @Autowired
    private ProfilDeSanteRepository profilDeSanteRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private MaladieRepository maladieRepository;
    @Autowired
    private ObjectifSanteRepository objectifSanteRepository;
    @Autowired
    private AllergieRepository allergieRepository;
    @Autowired
    private PreferenceAlimentaireRepository preferenceAlimentaireRepository;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private NutritionixService nutritionixService;

    public List<ProfilDeSante> obtenirTousLesProfils() {
        return profilDeSanteRepository.findAll();
    }

    public ProfilDeSante obtenirProfilParId(Long id) {
        return profilDeSanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé"));
    }

    public ProfilDeSante ajouterProfil(Long utilisateurId, ProfilDeSante profilDeSante) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        profilDeSante.setUtilisateur(utilisateur);
        enregistrerAssociations(profilDeSante);
        return profilDeSanteRepository.save(profilDeSante);
    }

    @Transactional
    public ProfilDeSante mettreAJourProfil(Long id, ProfilDeSante profilDeSante) {
        ProfilDeSante existingProfil = obtenirProfilParId(id);

        existingProfil.setMaladies(profilDeSante.getMaladies());
        existingProfil.setObjectifs(profilDeSante.getObjectifs());
        existingProfil.setAllergies(profilDeSante.getAllergies());
        existingProfil.setPreferencesAlimentaires(profilDeSante.getPreferencesAlimentaires());

        Utilisateur utilisateur = utilisateurRepository.findById(profilDeSante.getUtilisateur().getId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        utilisateur.setNom(profilDeSante.getUtilisateur().getNom());
        utilisateurRepository.save(utilisateur);

        return profilDeSanteRepository.save(existingProfil);
    }

    public void supprimerProfil(Long id) {
        ProfilDeSante profil = obtenirProfilParId(id);
        profilDeSanteRepository.delete(profil);
    }

    public ProfilDeSante obtenirProfilParEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .map(Utilisateur::getProfilDeSante)
                .orElseThrow(() -> new EntityNotFoundException("Profil non trouvé pour l'email donné"));
    }

    public Long getUtilisateurIdByEmail(String email) {
        return utilisateurService.rechercherParEmail(email)
                .map(Utilisateur::getId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email donné"));
    }

    public PlanRepas genererPlanRepas(Long profilId) {
        ProfilDeSante profil = obtenirProfilParId(profilId);
        PlanRepas planRepas = new PlanRepas();

        List<RepasSuggestion> petitDejeuner = filtrerRecettesParProfil(profil, "petit déjeuner");
        traiterRepasSuggestions(petitDejeuner);
        planRepas.setPetitDejeuner(petitDejeuner);

        List<RepasSuggestion> dejeuner = filtrerRecettesParProfil(profil, "déjeuner");
        traiterRepasSuggestions(dejeuner);
        planRepas.setDejeuner(dejeuner);

        List<RepasSuggestion> diner = filtrerRecettesParProfil(profil, "dîner");
        traiterRepasSuggestions(diner);
        planRepas.setDiner(diner);

        return planRepas;
    }

    private void traiterRepasSuggestions(List<RepasSuggestion> repasSuggestions) {
        for (RepasSuggestion repas : repasSuggestions) {
            System.out.println("Recherche d'informations nutritionnelles pour : " + repas.getFoodName());
            NutritionInfo nutritionInfo = nutritionixService.obtenirInfoNutritionnelle(repas.getFoodName());
            repas.setNutritionInfo(nutritionInfo != null ? nutritionInfo : new NutritionInfo(0.0, 0.0, 0.0, 0.0));

            List<String> ingredients = nutritionixService.obtenirIngredientsPourRepas(repas.getFoodName());
            repas.setIngredients(ingredients != null && !ingredients.isEmpty() ? ingredients : Collections.emptyList());
        }
    }

    private List<RepasSuggestion> filtrerRecettesParProfil(ProfilDeSante profil, String typeRepas) {
        String query = genererRequeteProfil(profil) + " " + typeRepas;
        ResponseEntity<String> response = nutritionixService.obtenirSuggestionsRepas(query);
        List<RepasSuggestion> suggestions = parseSuggestions(response.getBody());

        return filtrerSuggestions(suggestions, profil);
    }

    private String genererRequeteProfil(ProfilDeSante profil) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean estVegetarien = false;
        boolean estVegan = false;

        for (PreferenceAlimentaire preference : profil.getPreferencesAlimentaires()) {
            switch (preference.getNom().toLowerCase()) {
                case "végétarien" -> {
                    queryBuilder.append("végétarien ");
                    estVegetarien = true;
                }
                case "vegan" -> {
                    queryBuilder.append("vegan ");
                    estVegan = true;
                }
                case "carnivore" -> queryBuilder.append("carnivore ");
            }
        }

        profil.getMaladies().forEach(maladie -> {
            switch (maladie.getNom().toLowerCase()) {
                case "diabète" -> queryBuilder.append("faible en sucre ");
                case "hypertension" -> queryBuilder.append("faible en sel ");
            }
        });

        profil.getObjectifs().forEach(objectif -> {
            switch (objectif.getNom().toLowerCase()) {
                case "perte de poids" -> queryBuilder.append("faible en calories ");
                case "rester en forme" -> queryBuilder.append("équilibré ");
            }
        });

        profil.getAllergies().forEach(allergie -> queryBuilder.append("sans ").append(allergie.getNom()).append(" "));

        if (estVegetarien || estVegan) {
            queryBuilder.append("sans viande ");
        }

        return URLEncoder.encode(queryBuilder.toString().trim(), StandardCharsets.UTF_8);
    }

    private void enregistrerAssociations(ProfilDeSante profilDeSante) {
        profilDeSante.setMaladies(sauvegarderEntities(profilDeSante.getMaladies(), maladieRepository));
        profilDeSante.setObjectifs(sauvegarderEntities(profilDeSante.getObjectifs(), objectifSanteRepository));
        profilDeSante.setAllergies(sauvegarderEntities(profilDeSante.getAllergies(), allergieRepository));
        profilDeSante.setPreferencesAlimentaires(sauvegarderEntities(profilDeSante.getPreferencesAlimentaires(), preferenceAlimentaireRepository));
    }

    private <T> Set<T> sauvegarderEntities(Set<T> entities, JpaRepository<T, Long> repository) {
        return entities.stream()
                .map(repository::save)
                .collect(Collectors.toSet());
    }

    private List<RepasSuggestion> parseSuggestions(String suggestionsRecettes) {
        List<RepasSuggestion> repasSuggestions = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(suggestionsRecettes);
            JsonNode foods = rootNode.path("common");
            for (JsonNode food : foods) {
                String foodName = food.path("food_name").asText();
                int servingQty = food.path("serving_qty").asInt();
                String servingUnit = food.path("serving_unit").asText();
                String photo = food.path("photo").path("thumb").asText();
                repasSuggestions.add(new RepasSuggestion(foodName, servingQty, servingUnit, photo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repasSuggestions;
    }

    private List<RepasSuggestion> filtrerSuggestions(List<RepasSuggestion> suggestions, ProfilDeSante profil) {
        List<String> allergies = profil.getAllergies().stream()
                .map(Allergie::getNom)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        boolean estVegetarien = profil.getPreferencesAlimentaires().stream()
                .anyMatch(preference -> preference.getNom().equalsIgnoreCase("végétarien"));
        boolean estVegan = profil.getPreferencesAlimentaires().stream()
                .anyMatch(preference -> preference.getNom().equalsIgnoreCase("vegan"));

        return suggestions.stream()
                .filter(suggestion -> {
                    String foodName = suggestion.getFoodName().toLowerCase();
                    boolean respectsDiet = !(estVegetarien || estVegan) || (!foodName.contains("boeuf") && !foodName.contains("poulet") &&
                            !foodName.contains("porc") && !foodName.contains("poisson") && !foodName.contains("viande"));
                    boolean hasNoAllergies = allergies.stream().noneMatch(foodName::contains);

                    return respectsDiet && hasNoAllergies;
                })
                .collect(Collectors.toList());
    }
}
