package com.nuitriapp.equilibro.model;


import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients; // Liste des ingrédients

    private int cookingTime; // Temps de cuisson en minutes


}
