package com.nuitriapp.equilibro.model;


import com.fasterxml.jackson.databind.JsonNode;
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

    @ElementCollection
    private List<String> ingredients; // Liste des ingrédients

    private int cookingTime; // Temps de cuisson en minutes

    @Transient
    private JsonNode nutritionalInfo; // Informations nutritionnelles

    private String category; // Catégorie (végétarien ou carnivore)

}
