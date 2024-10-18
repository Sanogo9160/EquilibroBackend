package com.nuitriapp.equilibro.model;

import lombok.Data;

import java.util.List;

@Data
public class RecetteResponse {
    private List<Recette> recettes;
    private int count;

}

  /*  private List<Hit> hits;

    @Data
    public static class Hit {
        private Recipe recipe;
    }

    @Data
    public static class Recipe {
        private String label;
        private String image;
        private List<String> dietLabels;
        private List<String> healthLabels;
        private List<String> ingredientLines;
        private double calories;
    }
}


   */