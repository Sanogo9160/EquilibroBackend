package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class ProgrammeExercice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private String niveau;

    @ManyToOne
    private Utilisateur utilisateur;
}
