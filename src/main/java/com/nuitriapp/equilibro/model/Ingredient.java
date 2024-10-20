package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nom de l'ingrédient
    private String quantity; // Quantité de l'ingrédient

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe; // Association avec la recette

}
