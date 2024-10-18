package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Recette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;  // Nom de la recette
    private String url;    // Lien vers la recette complète
    private String source; // Source de la recette (ex. Edamam)
    private double calories;
    private double carbs;
    private double fat;
    private double protein;

    @ElementCollection
    private List<String> ingredients;

}
