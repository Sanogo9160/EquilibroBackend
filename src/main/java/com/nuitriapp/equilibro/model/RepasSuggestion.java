package com.nuitriapp.equilibro.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RepasSuggestion {

    private String foodName;
    private int servingQty;
    private String servingUnit;
    private String photo;

    private NutritionInfo nutritionInfo;
    private List<String> ingredients;

    // Constructeur avec paramètres
    public RepasSuggestion(String foodName, int servingQty, String servingUnit, String photo) {
        this.foodName = foodName;
        this.servingQty = servingQty;
        this.servingUnit = servingUnit;
        this.photo = photo;
        this.ingredients = new ArrayList<>();

    }


}
