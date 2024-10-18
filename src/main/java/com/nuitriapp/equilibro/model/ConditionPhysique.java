package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ConditionPhysique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double poids;
    private Double glycemie;
    private String pressionArterielle;
    private LocalDate date;

    @ManyToOne
    private Utilisateur utilisateur;


}