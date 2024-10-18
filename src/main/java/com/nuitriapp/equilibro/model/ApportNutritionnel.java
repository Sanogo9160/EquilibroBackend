package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ApportNutritionnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double calories;
    private Double proteines;
    private Double glucides;
    private Double lipides;
    private Double fibres;
    private Double sodium;

    @ManyToOne
    private Utilisateur utilisateur;

    private LocalDate date;

}