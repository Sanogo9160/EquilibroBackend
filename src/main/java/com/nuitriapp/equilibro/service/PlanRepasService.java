package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.ListeDeCourses;
import com.nuitriapp.equilibro.model.PlanRepas;
import com.nuitriapp.equilibro.model.RepasSuggestion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlanRepasService {

    public ListeDeCourses genererListeDeCourses(PlanRepas planRepas) {
        Set<String> ingredients = new HashSet<>(); // Utilisation d'un Set pour éviter les doublons

        // Ajoute les ingrédients de chaque repas dans la liste des courses
        ajouterIngredients(planRepas.getPetitDejeuner(), ingredients);
        ajouterIngredients(planRepas.getDejeuner(), ingredients);
        ajouterIngredients(planRepas.getDiner(), ingredients);

        ListeDeCourses listeDeCourses = new ListeDeCourses();
        listeDeCourses.setIngredients(new ArrayList<>(ingredients)); // Conversion en Liste
        return listeDeCourses;
    }

    private void ajouterIngredients(List<RepasSuggestion> repasSuggestions, Set<String> ingredients) {
        if (repasSuggestions != null) {
            for (RepasSuggestion repas : repasSuggestions) {

                ingredients.add(repas.getFoodName());
            }
        }
    }


}