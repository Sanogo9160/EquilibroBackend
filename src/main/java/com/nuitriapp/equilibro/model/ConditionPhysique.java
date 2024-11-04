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
    private Long profilId;
    private LocalDate date;
    private double poids;
    private double glycemie;
    private double pressionArterielle;

    /*
    @ManyToOne
    private Utilisateur utilisateur;

     */


}