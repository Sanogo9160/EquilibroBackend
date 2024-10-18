package com.nuitriapp.equilibro.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ApportsNutritionnels {
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

}