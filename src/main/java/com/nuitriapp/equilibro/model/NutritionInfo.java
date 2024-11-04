package com.nuitriapp.equilibro.model;

import lombok.Data;

@Data
public class NutritionInfo {
    private Double calories;
    private Double fat;
    private Double carbohydrates;
    private Double protein;

    public NutritionInfo(Double calories, Double fat, Double carbohydrates, Double protein) {
        this.calories = (calories != null) ? calories : 0.0; // Valeur par défaut
        this.fat = (fat != null) ? fat : 0.0; // Valeur par défaut
        this.carbohydrates = (carbohydrates != null) ? carbohydrates : 0.0; // Valeur par défaut
        this.protein = (protein != null) ? protein : 0.0; // Valeur par défaut
    }

}