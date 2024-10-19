package com.nuitriapp.equilibro.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label; // Nom de la recette
    private String image; // URL de l'image
    private String url;   // URL de la recette
    private List<String> ingredients; // Liste des ingrédients
    private int cookingTime; // Temps de cuisson en minutes

}
